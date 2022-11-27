package com.dimmythree.overflowclient.ui.adapters.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimmythree.overflowclient.R
import com.dimmythree.overflowclient.data.models.Tag
import com.dimmythree.overflowclient.ui.listeners.ListItemListener
import java.lang.ref.WeakReference

class TagViewHolder(
    private val containerView: View,
    private val listenerRef: WeakReference<ListItemListener>?
) : RecyclerView.ViewHolder(containerView) {

    fun bind(tag: Tag) {
        // Tag title
        val title = containerView.findViewById<TextView>(R.id.tag_item_title)
        title.text = tag.name

        containerView.setOnClickListener {
            listenerRef?.get()?.onTagClicked(tag)
        }
    }

}