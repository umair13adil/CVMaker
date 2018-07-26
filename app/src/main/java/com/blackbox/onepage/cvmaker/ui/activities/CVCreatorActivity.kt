package com.blackbox.onepage.cvmaker.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.utils.ColorUtils.darkenColor
import com.blackbox.onepage.cvmaker.utils.ColorUtils.lighterColor
import com.thebluealliance.spectrum.SpectrumDialog
import kotlinx.android.synthetic.main.activity_cv_creater.*


class CVCreatorActivity : AppCompatActivity() {

    val TAG: String = "CVCreatorActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_creater)

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
            val intent = Intent(this, CVViewerActivity::class.java)
            startActivity(intent)
        }

    }


    fun pickColor() {
        SpectrumDialog.Builder(this)
                .setColors(R.array.demo_colors)
                .setSelectedColorRes(R.color.md_blue_100)
                .setDismissOnColorSelected(false)
                .setOutlineWidth(2)
                .setOnColorSelectedListener { positiveResult, color ->
                    if (positiveResult) {
                        setThemeColor(color)
                    }
                }.build().show(supportFragmentManager, "dialog")
    }


    private fun setThemeColor(color: Int) {
        main_content?.setBackgroundColor(lighterColor(color))
        toolbar?.setBackgroundColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.window?.statusBarColor = darkenColor(color)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
        }
    }
}
