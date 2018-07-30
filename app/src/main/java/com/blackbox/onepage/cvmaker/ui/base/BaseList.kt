package com.blackbox.onepage.cvmaker.ui.base

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.IFlexible

class BaseList(var context: Context, var recyclerView: RecyclerView) : FlexibleAdapter.OnItemClickListener {

    init {
        setUpAdapter()
    }

    //List Adapter
    var adapter: FlexibleAdapter<*>? = null
    private var layoutManager: LinearLayoutManager? = null
    private var mItems: ArrayList<IFlexible<*>>? = null

    private val TAG = "BaseList"


    /**
     * This will setup list adapter.
     */
    fun setUpAdapter() {

        adapter = FlexibleAdapter(mItems, this, true)

        layoutManager = SmoothScrollLinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    //List Adapter's Callback
    override fun onItemClick(view: View?, position: Int): Boolean {

        return true
    }

    /***
     * This will clear all items from list.
     ***/
    fun clearListData() {
        try {
            if (isAdapterNotEmpty()) {
                mItems?.clear()
                adapter?.clear()
                adapter?.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /***
     * Will return true if items are added in adapter.
     ***/
    private fun isAdapterNotEmpty(): Boolean {
        return mItems != null && mItems?.isNotEmpty()!!
    }

    fun <T : IFlexible<*>> addItem(itemType: T) {

        if (mItems == null)
            mItems = arrayListOf<IFlexible<*>>()

        try {

            //Add Item to List
            mItems?.add(itemType)

            adapter?.updateDataSet(mItems!! as List<Nothing>)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * This will return parent task item from list.
     */
    fun <T : IFlexible<*>> getListItem(position: Int): T {
        return adapter?.getItem(position) as Nothing
    }
}
