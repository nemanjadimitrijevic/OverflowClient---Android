package com.dimmythree.overflowclient.ui.tags

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimmythree.overflowclient.data.MyResult
import com.dimmythree.overflowclient.data.Repository
import com.dimmythree.overflowclient.data.models.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var currentPage = 1
    private var tagsCount = 20

    private val _tags = MutableLiveData<MyResult<MutableList<Tag>>>()
    val tags = _tags
    private var items: MutableList<Tag>? = null

    var isLoadMore: Boolean = false

    init {
        isLoadMore = false
        getTags(currentPage, tagsCount)
    }

    private fun getTags(page: Int, count: Int) {
        viewModelScope.launch {
            repository.getTags(page, count).collect {
                when (it?.status) {
                    MyResult.Status.LOADING -> {
                        if(!isLoadMore) {
                            _tags.value = MyResult.loading()
                        }
                    }
                    MyResult.Status.SUCCESS -> {
                        val tagItems = it.data?.tags?.toMutableList()
                        if (items.isNullOrEmpty()) {
                            items = tagItems
                        } else {
                            items?.addAll(tagItems!!)
                        }
                        val hasMore = it.data?.hasMore ?: false
                        _tags.value = MyResult.success(items, hasMore)
                    }
                    MyResult.Status.ERROR -> {
                        // TODO - better error handling that include load more
                        _tags.value = MyResult.error(message = it.message ?: "", myError = it.myError)
                    }
                    else -> {}
                }
            }
        }
    }

    fun loadNextPage() {
        isLoadMore = true
        getTags(++currentPage, tagsCount)
    }
}