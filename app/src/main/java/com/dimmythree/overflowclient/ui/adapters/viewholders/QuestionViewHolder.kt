package com.dimmythree.overflowclient.ui.adapters.viewholders

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimmythree.overflowclient.R
import com.dimmythree.overflowclient.data.models.Question
import com.dimmythree.overflowclient.ui.listeners.ListItemListener
import com.dimmythree.overflowclient.util.DateUtils.getFormattedDate
import java.lang.ref.WeakReference

class QuestionViewHolder(
    private val containerView: View,
    private val listenerRef: WeakReference<ListItemListener>?
) : RecyclerView.ViewHolder(containerView) {

    fun bind(question: Question) {
        // Title
        val titleView = containerView.findViewById<TextView>(R.id.question_item_title)
        titleView.text = Html.fromHtml(question.title, Html.FROM_HTML_MODE_COMPACT)

        // Date
        val dateView = containerView.findViewById<TextView>(R.id.question_item_date)
        dateView.text = question.creationDate?.getFormattedDate()

        // Author
        val authorView = containerView.findViewById<TextView>(R.id.question_item_author)
        val author = Html.fromHtml(question.owner?.displayName, Html.FROM_HTML_MODE_COMPACT)
        authorView.text = containerView.context.getString(R.string.question_author, author)

        containerView.setOnClickListener {
            listenerRef?.get()?.onQuestionClicked(question)
        }
    }

}