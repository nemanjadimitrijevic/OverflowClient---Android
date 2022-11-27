package com.dimmythree.overflowclient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimmythree.overflowclient.R
import com.dimmythree.overflowclient.data.models.Question
import com.dimmythree.overflowclient.ui.adapters.viewholders.QuestionViewHolder
import com.dimmythree.overflowclient.ui.listeners.ListItemListener
import java.lang.ref.WeakReference

class QuestionListAdapter(private val listenerRef: WeakReference<ListItemListener>?) : LoadMoreAdapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
    }

    private var items: MutableList<Question> = mutableListOf()

    init {
        this.listenerRef?.get()?.also { setLoadMoreListener(it) }
        showLoadMore = true
    }

    override fun getViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuestionViewHolder(inflater.inflate(R.layout.item_list_question, parent, false), listenerRef)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, viewType: Int) {
        val item = items[position]
        (holder as QuestionViewHolder).bind(item)
    }

    override fun getCount(): Int {
        return items.count()
    }

    fun changeItems(items: MutableList<Question>?) {
        if (items != null) this.items = items

        isLoadingData = false

        notifyDataSetChanged()
    }


}