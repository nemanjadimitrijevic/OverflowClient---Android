package com.dimmythree.overflowclient.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimmythree.overflowclient.R
import com.dimmythree.overflowclient.data.MyResult
import com.dimmythree.overflowclient.data.models.Tag
import com.dimmythree.overflowclient.databinding.FragmentTagListBinding
import com.dimmythree.overflowclient.ui.BaseFragment
import com.dimmythree.overflowclient.ui.adapters.TagListAdapter
import com.dimmythree.overflowclient.ui.listeners.ListItemListener
import com.dimmythree.overflowclient.ui.questions.QuestionListFragment
import com.dimmythree.overflowclient.util.gone
import com.dimmythree.overflowclient.util.visible
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class TagListFragment : BaseFragment(), ListItemListener {

    private var _binding: FragmentTagListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TagListViewModel>()
    private var listAdapter: TagListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribe()
    }

    private fun initialize() {
        if (listAdapter == null)
            listAdapter = TagListAdapter(listenerRef = WeakReference(this))

        val tagList = binding.tagList
        val layoutManager = LinearLayoutManager(requireContext())
        tagList.layoutManager = layoutManager
        tagList.setHasFixedSize(true)
        tagList.adapter = listAdapter

    }

    private fun subscribe() {
        viewModel.tags.observe(viewLifecycleOwner) { result ->
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

                    val tagItems = result.data
                    if (!tagItems.isNullOrEmpty()) {
                        listAdapter?.changeItems(tagItems.toMutableList())
                    } else {
                        showError(binding.root)
                    }

                }
                MyResult.Status.ERROR -> {
                    stopProgress()
                    showError(binding.root, result.message)
                }
            }
        }
    }

    override fun onTagClicked(item: Tag?) {
        super.onTagClicked(item)
        val bundle = bundleOf(QuestionListFragment.ARG_TAG to item?.name)
        findNavController().navigate(R.id.action_TagsFragment_to_QuestionsFragment, bundle)
    }

    override fun onLoadMore() = viewModel.loadNextPage()

    override fun startProgress() {
        binding.tagProgress.visible()
    }

    override fun stopProgress() {
        binding.tagProgress.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}