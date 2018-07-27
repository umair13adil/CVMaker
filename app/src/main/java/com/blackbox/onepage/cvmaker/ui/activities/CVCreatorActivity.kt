package com.blackbox.onepage.cvmaker.ui.activities

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.ui.base.BaseActivity
import com.blackbox.onepage.cvmaker.ui.dialogs.TextInputDialog
import com.thebluealliance.spectrum.SpectrumDialog
import kotlinx.android.synthetic.main.activity_cv_creater.*
import kotlinx.android.synthetic.main.layout_cv_complete_page.*
import kotlinx.android.synthetic.main.layout_cv_header.*
import timber.log.Timber


class CVCreatorActivity : BaseActivity(), TextInputDialog.OnTextInput {

    val TAG: String = "CVCreatorActivity"

    private lateinit var viewModel: CVViewModel

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
            viewModel.createPDF(layout_cv)
        }


        img_cv.setOnLongClickListener {
            Timber.i("Long Pressed")
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSelection))
            false
        }

        //Set CV Id
        viewModel.setCVId()

        //Get Saved CV if any
        viewModel.getCVData()

        //Get saved color if any
        if (viewModel.cvData.themeColor != null)
            viewModel.setThemeColor(this, viewModel.cvData.themeColor!!)

        //Set saved data to UI
        viewModel.setDataForId(this)
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


}
