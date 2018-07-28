package com.blackbox.onepage.cvmaker.ui.activities

import android.arch.lifecycle.ViewModel
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.MainApplication
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.data.local.CVData
import com.blackbox.onepage.cvmaker.data.repositories.CVRepository
import com.blackbox.onepage.cvmaker.ui.base.BaseActivity
import com.blackbox.onepage.cvmaker.utils.ColorUtils
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Preferences
import com.otaliastudios.printer.DocumentView
import com.otaliastudios.printer.PdfPrinter
import com.otaliastudios.printer.PrintCallback
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.realm.Realm
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class CVViewModel @Inject constructor(private var cvRepository: CVRepository, private var app: MainApplication) : ViewModel() {

    var cvData = CVData()

    fun saveThemeColor(color: Int) {

        Realm.getDefaultInstance().executeTransaction {
            cvData.themeColor = color
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
            Picasso.with(activity).load(File(cvData.cvImage)).into((getView(activity, R.id.img_cv) as CircleImageView?))
        }
    }

    fun getCVData(): CVData {
        cvData = cvRepository.getData(1)
        return cvData
    }

    fun setThemeColor(activity: BaseActivity, color: Int) {
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

    fun createPDF(layout: DocumentView) {

        val callback = object : PrintCallback {
            override fun onPrintFailed(id: String?, error: Throwable?) {
                error?.printStackTrace()
            }

            override fun onPrint(id: String?, file: File?) {
                Timber.i("Printed: $id")
            }

        }

        val mPrinter = PdfPrinter(layout, callback)
        mPrinter.setPrintPageBackground(true)
        mPrinter.print(cvData.fullName, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), cvData.fullName + ".pdf")
    }
}