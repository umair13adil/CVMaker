package com.blackbox.onepage.cvmaker.ui.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackbox.onepage.cvmaker.R
import com.blackbox.onepage.cvmaker.models.Interest
import com.blackbox.onepage.cvmaker.utils.Constants
import com.blackbox.onepage.cvmaker.utils.Utils
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_interests.view.*

class InterestItem(val item: Interest) : AbstractFlexibleItem<InterestItem.ParentViewHolder>() {

    init {
        isDraggable = false
        isSwipeable = false
        isSelectable = true
    }

    override fun equals(o: Any?): Boolean {
        if (o is InterestItem) {
            val inItem = o as InterestItem?
            return this.item == inItem!!.item
        }
        return false
    }

    override fun hashCode(): Int {
        return item.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_interests
    }


    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?): ParentViewHolder {
        return ParentViewHolder(view!!, adapter!!)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>, viewHolder: ParentViewHolder, position: Int, payloads: MutableList<Any>?) {

        viewHolder.interest.text = item.interest
        //viewHolder.imageView.text = item.text2
    }

    class ParentViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter) {

        var interest: TextView
        var imageView: ImageView

        init {
            this.interest = view.txt_interest
            this.imageView = view.img_interest

            //Apply Fonts
            interest.typeface = Utils.getInstance().setTypeface(Constants.FONT_ROBOTO_BOLD, itemView.context)
        }

    }
}