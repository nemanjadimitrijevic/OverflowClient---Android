package com.dimmythree.overflowclient.ui.listeners

import com.dimmythree.overflowclient.data.models.Question
import com.dimmythree.overflowclient.data.models.Tag
import com.dimmythree.overflowclient.ui.adapters.LoadMoreAdapter

interface ListItemListener : LoadMoreAdapter.LoadMoreListener {

    fun onTagClicked(item: Tag?) {}
    fun onQuestionClicked(item: Question?) {}

}