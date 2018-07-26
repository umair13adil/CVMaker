package com.blackbox.onepage.cvmaker.ui.activities

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.View
import android.view.View.GONE
import com.blackbox.onepage.cvmaker.R
import com.hendrix.pdfmyxml.PdfDocument
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_cv_viewer.*
import java.io.File
import java.io.IOException


class CVViewerActivity : AppCompatActivity() {

    val TAG: String = "CVViewerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_viewer)

        btn_export.setOnClickListener { view ->
            createPDF()
        }

    }


    fun createPDF() {
        val page = object : AbstractViewRenderer(this, R.layout.activity_cv_viewer) {

            override fun initView(view: View) {

            }
        }

        // you can reuse the bitmap if you want
        page.isReuseBitmap = true


        PdfDocument.Builder(this).addPage(page).filename("test").orientation(PdfDocument.A4_MODE.PORTRAIT)
                .progressMessage(R.string.gen_pdf_file).progressTitle(R.string.please_wait).renderWidth(2480).renderHeight(3508)
                .listener(object : PdfDocument.Callback {
                    override fun onComplete(file: File) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete")

                        val output = Environment.getExternalStorageDirectory().toString() + File.separator + "Documents" + File.separator + "MY_PDF.pdf"

                        /*try {
                            FileUtils.copy(file, File(output))
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }*/

                    }

                    override fun onError(e: Exception) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error")
                    }
                }).create().createPdf(this)
    }

}
