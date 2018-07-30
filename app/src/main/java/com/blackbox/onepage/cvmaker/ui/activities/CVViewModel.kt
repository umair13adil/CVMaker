package com.blackbox.onepage.cvmaker.ui.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blackbox.onepage.cvmaker.MainApplication
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.data.local.CVData
import com.blackbox.onepage.cvmaker.data.repositories.CVRepository
import com.blackbox.onepage.cvmaker.ui.base.BaseActivity
import com.blackbox.onepage.cvmaker.utils.ColorUtils
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Preferences
import com.blackbox.onepage.cvmaker.utils.Utils
import com.otaliastudios.printer.DocumentView
import com.otaliastudios.printer.PdfPrinter
import com.otaliastudios.printer.PrintCallback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Single
import io.realm.Realm
import java.io.File
import javax.inject.Inject


class CVViewModel @Inject constructor(private var cvRepository: CVRepository, private var app: MainApplication) : ViewModel() {

    var cvData = CVData()

    private var selectedColor = R.color.colorPrimaryDark

    fun saveThemeColor(color: Int) {

        Realm.getDefaultInstance().executeTransaction {
            cvData.themeColor = color
            selectedColor = color
        }
        cvRepository.saveData(cvData)
    }


    fun saveImagePath(path: String) {

        Realm.getDefaultInstance().executeTransaction {
            cvData.cvImage = path
        }
        cvRepository.saveData(cvData)
    }

    fun dataForId(text: String, id: Int) {

        Realm.getDefaultInstance().executeTransaction {

            when (id) {
            //CV Header Info
                R.id.txt_full_name -> cvData.fullName = text
                R.id.txt_head_line -> cvData.headline = text
                R.id.txt_about_me -> cvData.aboutMe = text
                R.id.txt_email -> cvData.email = text
                R.id.txt_address -> cvData.address = text
                R.id.txt_phone -> cvData.phone = text
                R.id.txt_social -> cvData.social = text
            }
        }

        cvRepository.saveData(cvData)
    }

    fun setDataForId(activity: BaseActivity) {

        //CV Header Info
        getTextView(activity, R.id.txt_full_name)?.text = cvData.fullName
        getTextView(activity, R.id.txt_head_line)?.text = cvData.headline
        getTextView(activity, R.id.txt_about_me)?.text = cvData.aboutMe
        getTextView(activity, R.id.txt_email)?.text = cvData.email
        getTextView(activity, R.id.txt_address)?.text = cvData.address
        getTextView(activity, R.id.txt_phone)?.text = cvData.phone
        getTextView(activity, R.id.txt_social)?.text = cvData.social

        if (cvData.cvImage != null) {
            Picasso.with(activity)
                    .load(File(cvData.cvImage))
                    .placeholder(R.drawable.placeholder_male)
                    .into((getView(activity, R.id.img_cv) as CircleImageView?))
        }
    }

    fun getCVData(): CVData {
        cvData = cvRepository.getData(1)!!
        return cvData
    }

    fun setThemeColor(activity: BaseActivity, color: Int) {
        selectedColor = color

        getView(activity, R.id.main_content)?.setBackgroundColor(ColorUtils.lighterColor(color))
        getView(activity, R.id.toolbar)?.setBackgroundColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window?.statusBarColor = ColorUtils.darkenColor(color)
        }

        //CV Layout
        getView(activity, R.id.divider_header)?.setBackgroundColor(color)
        getTextView(activity, R.id.txt_head_line)?.setTextColor(color)
        (getView(activity, R.id.img_cv) as CircleImageView?)?.borderColor = color

        ColorUtils.setTextDrawableColor(getTextView(activity, R.id.txt_email)!!, color)
        ColorUtils.setTextDrawableColor(getTextView(activity, R.id.txt_phone)!!, color)
        ColorUtils.setTextDrawableColor(getTextView(activity, R.id.txt_address)!!, color)
        ColorUtils.setTextDrawableColor(getTextView(activity, R.id.txt_social)!!, color)
    }

    private fun getTextView(activity: BaseActivity, id: Int): TextView? {
        return (getView(activity, id) as TextView)
    }

    private fun getView(activity: BaseActivity, id: Int): View? {
        return activity.findViewById(id)
    }

    fun setCVId() {
        var id = Preferences.getInstance().getInt(app.applicationContext, Constants.PREF_CV_ID, 0)

        //If new record then save first CV with id 1
        if (id == 0) {
            id = 1
            Preferences.getInstance().save(app.applicationContext, Constants.PREF_CV_ID, id)

            Realm.getDefaultInstance().executeTransaction {
                cvData.id = id
            }

            cvRepository.saveData(cvData)
        }
    }

    fun createPDF(fileName: String, layout: DocumentView): Single<File> {

        return Single.create<File> {

            val callback = object : PrintCallback {
                override fun onPrintFailed(id: String?, error: Throwable?) {
                    error?.printStackTrace()
                    it.onError(error!!)
                }

                override fun onPrint(id: String?, file: File?) {
                    it.onSuccess(file!!)
                }

            }

            val mPrinter = PdfPrinter(layout, callback)
            mPrinter.setPrintPageBackground(true)
            mPrinter.print(fileName, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "$fileName.pdf")
        }
    }

    @SuppressLint("ResourceAsColor")
    fun showHideEditingOptions(rootView: ViewGroup, show: Boolean) {

        val context = app.applicationContext
        val buttonTag = context.getString(R.string.tag_edit_button)
        val textTag = context.getString(R.string.tag_edit_textview)
        val borderTag = context.getString(R.string.tag_border_layout)

        val buttonList = Utils.getInstance().getViewsByTag(rootView, buttonTag)
        val textList = Utils.getInstance().getViewsByTag(rootView, textTag)
        val borderList = Utils.getInstance().getViewsByTag(rootView, borderTag)

        val addDrawable = ContextCompat.getDrawable(context, R.drawable.ic_add)
        addDrawable?.colorFilter = PorterDuffColorFilter(selectedColor, PorterDuff.Mode.SRC_IN)

        for (button in buttonList) {
            if (!show)
                button.visibility = View.GONE
            else
                button.visibility = View.VISIBLE
        }

        for (text in textList) {
            if (!show)
                (text as TextView).setCompoundDrawables(null, null, null, null)
            else
                (text as TextView).setCompoundDrawables(addDrawable, null, null, null)
        }

        for (border in borderList) {
            if (!show)
                border.setBackgroundResource(0)
            else
                border.setBackgroundResource(R.drawable.border_background)
        }
    }
}