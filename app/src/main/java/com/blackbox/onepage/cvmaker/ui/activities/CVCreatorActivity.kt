package com.blackbox.onepage.cvmaker.ui.activities

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.models.*
import com.blackbox.onepage.cvmaker.ui.base.BaseActivity
import com.blackbox.onepage.cvmaker.ui.base.BaseList
import com.blackbox.onepage.cvmaker.ui.dialogs.SkillsAddDialog
import com.blackbox.onepage.cvmaker.ui.dialogs.TextInputDialog
import com.blackbox.onepage.cvmaker.ui.items.*
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Utils
import com.squareup.picasso.Picasso
import com.thebluealliance.spectrum.SpectrumDialog
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_cv_creater.*
import kotlinx.android.synthetic.main.layout_achievements.*
import kotlinx.android.synthetic.main.layout_cv_complete_page.*
import kotlinx.android.synthetic.main.layout_cv_header.*
import kotlinx.android.synthetic.main.layout_education.*
import kotlinx.android.synthetic.main.layout_experience.*
import kotlinx.android.synthetic.main.layout_interests.*
import kotlinx.android.synthetic.main.layout_skills.*
import java.io.File


class CVCreatorActivity : BaseActivity(), TextInputDialog.OnTextInput {

    val TAG: String = "CVCreatorActivity"

    private lateinit var viewModel: CVViewModel
    private val REQUEST_CODE_CHOOSE = 1232

    //Lists
    var educationList: BaseList? = null
    var experienceList: BaseList? = null
    var achievementList: BaseList? = null
    var skillsList: BaseList? = null
    var interestsList: BaseList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_creater)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CVViewModel::class.java)

        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@CVCreatorActivity, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 2222)
        }

        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@CVCreatorActivity, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 22223)
        }

        fab.setOnClickListener {
            pickColor()
        }

        fab_view.setOnClickListener {

            //Hide editing options
            viewModel.showHideEditingOptions(layout_cv, false)

            showLoading(progress_bar, layout_cv)

            viewModel.createPDF("my_cv", layout_cv)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(  // named arguments for lambda Subscribers
                            onSuccess = {
                                showToast(getString(R.string.txt_cv_created))
                                hideLoading(progress_bar, layout_cv)
                                Utils.getInstance().showPDF(it, this)
                            },
                            onError = {
                                showToast(getString(R.string.txt_cv_create_fail))
                                it.printStackTrace()
                                hideLoading(progress_bar, layout_cv)
                            }
                    )
        }


        img_cv.setOnClickListener {

            Matisse.from(this)
                    .choose(MimeType.of(MimeType.JPEG))
                    .countable(true)
                    .maxSelectable(9)
                    .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(PicassoEngine())
                    .maxSelectable(1)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, Constants.APP_AUTHORITY))
                    .forResult(REQUEST_CODE_CHOOSE)
        }

        //Get Saved CV if any
        viewModel.getCVData()

        //Set CV Id
        viewModel.setCVId()

        //Get saved color if any
        if (viewModel.cvData.themeColor != null)
            viewModel.setThemeColor(this, viewModel.cvData.themeColor!!)

        //Set saved data to UI
        viewModel.setDataForId(this)

        setupLists()
    }

    override fun onInput(text: String, view: View) {
        (view as TextView).text = text
        viewModel.dataForId(text, view.id)
    }

    fun showTextInputDialog(view: View) {

        val dialog: DialogFragment = TextInputDialog().also {
            val text = (view as TextView).text.toString()
            it.setCallback(this, text, view)
        }

        dialog.show(supportFragmentManager, "dialogInput")
    }

    private fun pickColor() {
        SpectrumDialog.Builder(this)
                .setColors(R.array.demo_colors)
                .setSelectedColorRes(R.color.md_blue_100)
                .setDismissOnColorSelected(false)
                .setOutlineWidth(2)
                .setOnColorSelectedListener { positiveResult, color ->
                    if (positiveResult) {
                        viewModel.setThemeColor(this, color)
                        viewModel.saveThemeColor(color)
                    }
                }.build().show(supportFragmentManager, "dialog")
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val path = Matisse.obtainPathResult(data).first()
            Picasso.with(this@CVCreatorActivity).load(File(path)).into(img_cv)
            viewModel.saveImagePath(path)
        }
    }


    override fun onResume() {
        super.onResume()

        //Show editing options
        viewModel.showHideEditingOptions(layout_cv, true)
    }

    private fun setupLists() {

        educationList = BaseList(this, list_education)
        experienceList = BaseList(this, list_experience)
        achievementList = BaseList(this, list_achievement)
        skillsList = BaseList(this, list_skills)
        interestsList = BaseList(this, list_interests)

        //Education
        educationList?.addItem(EducationItem(Education("Degree Name Here", "Institute Name Here", "Time Period Here", "Institute Location Here", RealmList<BulletPoints>(BulletPoints("Line 1"), BulletPoints("Line 1")))))

        //Experience
        experienceList?.addItem(ExperienceItem(Experience("Job Title Here", "Organization Name Here", "Time Period Here", "Organization Location Here", RealmList<BulletPoints>(BulletPoints("Line 1"), BulletPoints("Line 1")))))

        //Achievement
        achievementList?.addItem(AchievementItem(Achievement("Achievement 1", "Achievement 2")))

        //Skills
        skillsList?.addItem(SkillItem(Skill("Skill 1")))

        //Interests
        interestsList?.addItem(InterestItem(Interest(R.drawable.ic_right_arrow, "Interest 1")))

        btn_add_education.setOnClickListener {

        }

        btn_add_experience.setOnClickListener {

        }

        btn_add_achievement.setOnClickListener {

        }

        //Skills input Listener
        btn_add_skills.setOnClickListener {
            showSkillsInputDialog()
        }

        btn_add_interests.setOnClickListener {

        }
    }

    private fun showSkillsInputDialog() {
        val dialog: DialogFragment = SkillsAddDialog().also {
            it.setCallback(object : SkillsAddDialog.OnSubmit {
                override fun submitted(list: List<Skill>) {
                    for (item in list) {
                        skillsList?.addItem(SkillItem(item))
                    }
                }
            })
        }
        dialog.show(supportFragmentManager, "skillDialog")
    }
}
