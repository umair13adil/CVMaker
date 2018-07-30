package com.blackbox.onepage.cvmaker.ui.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.models.BulletPoints
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Utils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_bullet_points.view.*

class BulletItem(val item: BulletPoints) : AbstractFlexibleItem<BulletItem.ParentViewHolder>() {

    init {
        isDraggable = false
        isSwipeable = false
        isSelectable = true
    }

    override fun equals(o: Any?): Boolean {
        if (o is BulletItem) {
            val inItem = o as BulletItem?
            return this.item == inItem!!.item
        }
        return false
    }

    override fun hashCode(): Int {
        return item.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_bullet_points
    }


    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?): ParentViewHolder {
        return ParentViewHolder(view!!, adapter!!)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, viewHolder: ParentViewHolder, position: Int, payloads: MutableList<Any>?) {

        viewHolder.point.text = item.point
    }

    class ParentViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

        var point: TextView

        init {
            this.point = view.txt_point

            //Apply Fonts
            point.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)
        }

    }
}