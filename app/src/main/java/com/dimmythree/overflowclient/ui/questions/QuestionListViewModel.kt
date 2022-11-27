package com.dimmythree.overflowclient.ui.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimmythree.overflowclient.data.MyResult
import com.dimmythree.overflowclient.data.Repository
import com.dimmythree.overflowclient.data.models.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var currentPage = 1
    private var questionsCount = 20

    private val _questions= MutableLiveData<MyResult<MutableList<Question>>>()
    val questions = _questions
    private var items: MutableList<Question>? = null

    private var isLoadMore: Boolean = false
    private var tagName: String? = null

    init {
        isLoadMore = false
    }


    fun getQuestions(tag: String?) {
        this.tagName = tag
        if(tagName == null){
            return
        }
        viewModelScope.launch {
            repository.getQuestionsForTag(tagName!!, currentPage, questionsCount).collect {
                when (it?.status) {
                    MyResult.Status.LOADING -> {
                        if(!isLoadMore) {
                            _questions.value = MyResult.loading()
                        }
                    }
                    MyResult.Status.SUCCESS -> {
                        val questionItems = it.data?.questions?.toMutableList()
                        if (items.isNullOrEmpty()) {
                            items = questionItems
                        } else {
                            items?.addAll(questionItems!!)
                        }
                        val hasMore = it.data?.hasMore ?: false
                        _questions.value = MyResult.success(items, hasMore)
                    }
                    MyResult.Status.ERROR -> {
                        // TODO - better error handling that include load more
                        _questions.value = MyResult.error(message = it.message ?: "", myError = it.myError)
                    }
                    else -> {}
                }
            }
        }
    }

    fun loadNextPage() {
        isLoadMore = true
        getQuestions(tagName)
    }

}