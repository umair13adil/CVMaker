package com.blackbox.onepage.cvmaker.ui.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.models.Experience
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Utils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_experience.view.*

class ExperienceItem(val item: Experience) : AbstractFlexibleItem<ExperienceItem.ParentViewHolder>() {

    init {
        isDraggable = false
        isSwipeable = false
        isSelectable = true
    }

    override fun equals(o: Any?): Boolean {
        if (o is ExperienceItem) {
            val inItem = o as ExperienceItem?
            return this.item == inItem!!.item
        }
        return false
    }

    override fun hashCode(): Int {
        return item.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_experience
    }


    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?): ParentViewHolder {
        return ParentViewHolder(view!!, adapter!!)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, viewHolder: ParentViewHolder, position: Int, payloads: MutableList<Any>?) {

        viewHolder.designation.text = item.designation
        viewHolder.organization.text = item.organization
        viewHolder.time.text = item.timePeriod
        viewHolder.location.text = item.locaton
    }

    class ParentViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

        var designation: TextView
        var organization: TextView
        var location: TextView
        var time: TextView


        init {
            this.designation = view.txt_designation
            this.organization = view.txt_organization
            this.time = view.txt_time_period
            this.location = view.txt_organization_location


            //Apply Fonts
            designation.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)
            organization.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_REGULAR, itemView.context)
            time.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)
            location.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)

        }

    }
}