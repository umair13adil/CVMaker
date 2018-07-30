package com.blackbox.onepage.cvmaker.ui.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.models.Skill
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Utils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_skills.view.*

class SkillItem(val item: Skill) : AbstractFlexibleItem<SkillItem.ParentViewHolder>() {

    init {
        isDraggable = false
        isSwipeable = false
        isSelectable = true
    }

    override fun equals(o: Any?): Boolean {
        if (o is SkillItem) {
            val inItem = o as SkillItem?
            return this.item == inItem!!.item
        }
        return false
    }

    override fun hashCode(): Int {
        return item.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_skills
    }


    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?): ParentViewHolder {
        return ParentViewHolder(view!!, adapter!!)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, viewHolder: ParentViewHolder, position: Int, payloads: MutableList<Any>?) {

        viewHolder.skillName.text = item.skill
    }

    class ParentViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

        var skillName: TextView

        init {
            this.skillName = view.txt_skill

            //Apply Fonts
            skillName.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)
        }

    }
}