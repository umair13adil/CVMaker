package com.blackbox.onepage.cvmaker.ui.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.models.Achievement
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Utils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_achievement.view.*

class AchievementItem(val item: Achievement) : AbstractFlexibleItem<AchievementItem.ParentViewHolder>() {

    init {
        isDraggable = false
        isSwipeable = false
        isSelectable = true
    }

    override fun equals(o: Any?): Boolean {
        if (o is AchievementItem) {
            val inItem = o as AchievementItem?
            return this.item == inItem!!.item
        }
        return false
    }

    override fun hashCode(): Int {
        return item.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_achievement
    }


    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?): ParentViewHolder {
        return ParentViewHolder(view!!, adapter!!)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, viewHolder: ParentViewHolder, position: Int, payloads: MutableList<Any>?) {

        viewHolder.text_1.text = item.text1
        viewHolder.text_2.text = item.text2
    }

    class ParentViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

        var text_1: TextView
        var text_2: TextView

        init {
            this.text_1 = view.txt_line1
            this.text_2 = view.txt_line2

            //Apply Fonts
            text_1.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)
            text_2.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_REGULAR, itemView.context)
        }

    }
}