package com.blackbox.onepage.cvmaker.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v4.content.FileProvider
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import io.reactivex.annotations.NonNull
import java.io.File

/**
 * Created by umair on 31/05/2017.
 */

class Utils {

    companion object {

        @NonNull
        private val ourInstance = Utils()

        @NonNull
        fun getInstance(): Utils {
            return ourInstance
        }

    }

    fun setTypeface(@NonNull type: Int, @NonNull context: Context): Typeface {

        val assets = context.assets

        var font = Typeface.createFromAsset(assets, "fonts/Roboto-Black.ttf")

        when (type) {
            0 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Black.ttf")
            1 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-BlackItalic.ttf")
            2 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Bold.ttf")
            3 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-BoldItalic.ttf")
            4 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Italic.ttf")
            5 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Light.ttf")
            6 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-LightItalic.ttf")
            7 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Medium.ttf")
            8 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-MediumItalic.ttf")
            9 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Regular.ttf")
            10 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-Thin.ttf")
            11 -> font = Typeface.createFromAsset(assets, "fonts/Roboto-ThinItalic.ttf")
            12 -> font = Typeface.createFromAsset(assets, "fonts/RobotoCondensed-Bold.ttf")
            13 -> font = Typeface.createFromAsset(assets, "fonts/RobotoCondensed-BoldItalic.ttf")
            14 -> font = Typeface.createFromAsset(assets, "fonts/RobotoCondensed-Italic.ttf")
            15 -> font = Typeface.createFromAsset(assets, "fonts/RobotoCondensed-Light.ttf")
            16 -> font = Typeface.createFromAsset(assets, "fonts/RobotoCondensed-LightItalic.ttf")
            17 -> font = Typeface.createFromAsset(assets, "fonts/RobotoCondensed-Regular.ttf")
        }

        return font
    }

    fun hideKeyboard(context: Context?) {
        try {
            if (context != null) {
                val a = context as Activity?
                val imm = a!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(a.currentFocus!!.windowToken, 0)
            }
        } catch (e: NullPointerException) {

        }
    }

    fun getViewsByTag(root: ViewGroup, tag: String?): ArrayList<View> {
        val views = ArrayList<View>()
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) {
                views.addAll(getViewsByTag(child, tag))
            }

            val tagObj = child.tag
            if (tagObj != null && tagObj == tag) {
                views.add(child)
            }

        }
        return views
    }

    fun showPDF(file: File, activity: Activity) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = FileProvider.getUriForFile(activity, Constants.APP_AUTHORITY, file)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}
