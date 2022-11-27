package com.dimmythree.overflowclient.ui.questions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimmythree.overflowclient.data.MyResult
import com.dimmythree.overflowclient.data.models.Question
import com.dimmythree.overflowclient.databinding.FragmentTagQuestionsBinding
import com.dimmythree.overflowclient.ui.BaseFragment
import com.dimmythree.overflowclient.ui.adapters.QuestionListAdapter
import com.dimmythree.overflowclient.ui.listeners.ListItemListener
import com.dimmythree.overflowclient.util.gone
import com.dimmythree.overflowclient.util.visible
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference


@AndroidEntryPoint
class QuestionListFragment : BaseFragment(), ListItemListener {

    private var _binding: FragmentTagQuestionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuestionListViewModel>()
    private var listAdapter: QuestionListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTagQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tagName = arguments?.getString(ARG_TAG)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = tagName



        initialize()
        subscribe(tagName)
    }

    private fun initialize() {
        if (listAdapter == null)
            listAdapter = QuestionListAdapter(listenerRef = WeakReference(this))

        val tagList = binding.questionsList
        val layoutManager = LinearLayoutManager(requireContext())
        tagList.layoutManager = layoutManager
        tagList.setHasFixedSize(true)
        tagList.adapter = listAdapter

    }

    private fun subscribe(tagName: String?) {
        viewModel.getQuestions(tagName)
        viewModel.questions.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                MyResult.Status.LOADING -> {
                    startProgress()
                }
                MyResult.Status.SUCCESS -> {
                    stopProgress()
                    if (result.hasMore) {
                        listAdapter?.showLoadMore = true
                    } else {
                        listAdapter?.hideLoadMore()
                    }

                    val questionItems = result.data
                    if (!questionItems.isNullOrEmpty()) {
                        listAdapter?.changeItems(questionItems.toMutableList())
                    } else {
                        showError(binding.root)
                    }

                }
                MyResult.Status.ERROR -> {
                    stopProgress()
                    showError(binding.root)
                }
            }
        }
    }

    override fun onQuestionClicked(item: Question?) {
        super.onQuestionClicked(item)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item?.link)))
    }

    override fun onLoadMore() = viewModel.loadNextPage()

    override fun startProgress() {
        binding.questionsProgress.visible()
    }

    override fun stopProgress() {
        binding.questionsProgress.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_TAG = "tagName"
    }
}