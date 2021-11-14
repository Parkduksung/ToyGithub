package com.example.toygithub.ui.github

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.toygithub.R
import com.example.toygithub.base.BaseFragment
import com.example.toygithub.base.ViewState
import com.example.toygithub.databinding.FragmentGithubLocalBinding
import com.example.toygithub.ui.adapter.LocalAdapter
import com.example.toygithub.viewmodel.GithubHomeViewModel
import com.example.toygithub.viewmodel.GithubLocalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubLocalFragment :
    BaseFragment<FragmentGithubLocalBinding>(R.layout.fragment_github_local) {

    private val githubHomeViewModel by activityViewModels<GithubHomeViewModel>()

    private val githubLocalViewModel by viewModels<GithubLocalViewModel>()

    private val localAdapter by lazy { LocalAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }


    private fun initUi() {

        binding.rvLocal.run {
            adapter = localAdapter
        }

        localAdapter.setOnItemClickListener { entity ->
            githubLocalViewModel.deleteBookmark(entity)
        }
    }

    private fun initViewModel() {
        lifecycle.addObserver(githubLocalViewModel)

        binding.viewModel = githubLocalViewModel

        githubHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState: ViewState? ->
            (viewState as? GithubHomeViewModel.GithubViewState)?.let {
                onChangedHomeViewState(
                    viewState
                )
            }
        }

        githubLocalViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState: ViewState? ->
            (viewState as? GithubLocalViewModel.GithubLocalViewState)?.let {
                onChangedLocalViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedHomeViewState(viewState: GithubHomeViewModel.GithubViewState) {
        when (viewState) {
            is GithubHomeViewModel.GithubViewState.AddBookmark -> {
                localAdapter.add(viewState.item)
            }

            is GithubHomeViewModel.GithubViewState.DeleteBookmark -> {
                localAdapter.delete(viewState.item)
            }
        }
    }

    private fun onChangedLocalViewState(viewState: GithubLocalViewModel.GithubLocalViewState) {
        when (viewState) {
            is GithubLocalViewModel.GithubLocalViewState.DeleteBookmark -> {
                githubHomeViewModel.deleteBookmark(viewState.item)
            }

            is GithubLocalViewModel.GithubLocalViewState.GetBookmarkList -> {
                localAdapter.addAll(viewState.list)
            }

            is GithubLocalViewModel.GithubLocalViewState.ErrorGetBookmarkList -> {

            }

            is GithubLocalViewModel.GithubLocalViewState.EmptyGetBookmarkList -> {

            }

        }
    }
}
