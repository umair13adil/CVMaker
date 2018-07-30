package com.blackbox.onepage.cvmaker.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.data.local.RealmHelper
import com.blackbox.onepage.cvmaker.models.Skill
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_text_input.view.*
import javax.inject.Inject


class SkillsAddDialog : DialogFragment() {

    @Inject
    lateinit var db: RealmHelper

    interface OnSubmit {
        fun submitted(list: List<Skill>)
    }

    var onSubmitCallback: SkillsAddDialog.OnSubmit? = null


    fun setCallback(callBack: SkillsAddDialog.OnSubmit) {
        onSubmitCallback = callBack
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Dagger
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_add_skills, container, true)
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE)
        dialog.window.setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = true

        view.editText.requestFocus()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        return view
    }
}