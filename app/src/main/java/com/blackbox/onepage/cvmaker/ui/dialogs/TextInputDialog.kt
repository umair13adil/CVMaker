package com.blackbox.onepage.cvmaker.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.blackbox.onepage.cvmaker.R
import kotlinx.android.synthetic.main.dialog_text_input.*
import kotlinx.android.synthetic.main.dialog_text_input.view.*


class TextInputDialog : DialogFragment() {

    interface OnTextInput {
        fun onInput(text: String, view: View)
    }

    var onInputCallback: OnTextInput? = null
    var viewProvided: View? = null
    var textDisplay: String? = ""

    fun setCallback(callBack: OnTextInput, text1: String, view: View) {
        onInputCallback = callBack
        viewProvided = view
        textDisplay = text1
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_text_input, container, true)
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
        dialog.window.setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = true

        view.editText.setText(textDisplay)

        view.editText.requestFocus()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        view.editText?.setOnEditorActionListener { textView, i, keyEvent ->

            if (i == EditorInfo.IME_ACTION_DONE) {

                if (onInputCallback != null) {
                    onInputCallback?.onInput(editText?.text?.toString()!!, viewProvided!!)
                }

                dismiss()

                return@setOnEditorActionListener true
            }
            false
        }

        return view
    }
}