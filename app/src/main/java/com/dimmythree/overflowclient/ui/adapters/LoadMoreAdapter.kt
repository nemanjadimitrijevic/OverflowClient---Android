package com.dimmythree.overflowclient.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.dimmythree.overflowclient.R
import java.lang.ref.WeakReference

abstract class LoadMoreAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_LOAD_MORE = 99
    }

    private var loadMoreListener: WeakReference<LoadMoreListener>? = null

    var showLoadMore = false
    var isLoadingData = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_TYPE_LOAD_MORE)
            return LoadMoreHolder(inflater.inflate(R.layout.item_list_load_more, parent, false))

        return onCreateViewHolder(inflater, parent, viewType)
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        return if (showLoadMore && position != 0 && position == getCount())
            VIEW_TYPE_LOAD_MORE
        else
            getViewType(position)
    }

    @CallSuper
    override fun getItemCount(): Int {
        val count = getCount()
        return if (count > 0 && showLoadMore) count + 1 else count
    }

    @CallSuper
    override fun getItemId(position: Int): Long {
        if (position == getCount())
            return -999111L
        return getItemIdForPosition(position)
    }

    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): T

    abstract fun onBindViewHolder(holder: T, position: Int, viewType: Int)

    abstract fun getCount(): Int

    open fun getViewType(position: Int): Int {
        return 0
    }

    open fun getItemIdForPosition(position: Int): Long {
        return RecyclerView.NO_ID
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            if (!isLoadingData && loadMoreListener?.get() != null) {
                isLoadingData = true
                loadMoreListener?.get()?.onLoadMore()
            }
        }
        else {
            onBindViewHolder(holder as T, position, viewType)
        }
    }

    fun setLoadMoreListener(loadMoreListener: LoadMoreListener) {
        this.loadMoreListener = WeakReference(loadMoreListener)
    }

    fun hideLoadMore() {
        if (showLoadMore) {
            this.showLoadMore = false
            notifyItemRemoved(getCount())
        }
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }

}

private class LoadMoreHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)