package com.example.toygithub.ui.github

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.toygithub.R
import com.example.toygithub.base.BaseFragment
import com.example.toygithub.base.ViewState
import com.example.toygithub.databinding.FragmentGithubApiBinding
import com.example.toygithub.ui.adapter.ApiAdapter
import com.example.toygithub.viewmodel.GithubApiViewModel
import com.example.toygithub.viewmodel.GithubHomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GithubApiFragment : BaseFragment<FragmentGithubApiBinding>(R.layout.fragment_github_api) {

    private val githubHomeViewModel by activityViewModels<GithubHomeViewModel>()

    private val githubApiViewModel by viewModels<GithubApiViewModel>()

    private val apiAdapter by lazy { ApiAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }


    private fun initUi() {

        binding.rvApi.run {
            adapter = apiAdapter
        }
        apiAdapter.setOnItemClickListener { entity, isBookmark ->
            githubApiViewModel.toggleBookmark(entity, isBookmark)
            entity.login?.let {
                githubApiViewModel.searchUserRepos(it)
            }
        }
    }

    private fun initViewModel() {

        binding.viewModel = githubApiViewModel

        githubHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState: ViewState? ->
            (viewState as? GithubHomeViewModel.GithubHomeViewState)?.let {
                onChangedHomeViewState(
                    viewState
                )
            }
        }

        githubApiViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState: ViewState? ->
            (viewState as? GithubApiViewModel.GithubApiViewState)?.let {
                onChangedApiViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedHomeViewState(viewState: GithubHomeViewModel.GithubHomeViewState) {
        when (viewState) {
            is GithubHomeViewModel.GithubHomeViewState.AddBookmark -> {
                apiAdapter.updateItem(viewState.item)
            }

            is GithubHomeViewModel.GithubHomeViewState.DeleteBookmark -> {
                apiAdapter.updateItem(viewState.item)
            }
        }
    }

    private fun onChangedApiViewState(viewState: GithubApiViewModel.GithubApiViewState) {
        when (viewState) {
            is GithubApiViewModel.GithubApiViewState.AddBookmark -> {
                githubHomeViewModel.addBookmark(viewState.item)
            }

            is GithubApiViewModel.GithubApiViewState.DeleteBookmark -> {
                githubHomeViewModel.deleteBookmark(viewState.item)
            }

            is GithubApiViewModel.GithubApiViewState.GetSearchUser -> {
                apiAdapter.clear()
                apiAdapter.addAll(viewState.list)
            }

            is GithubApiViewModel.GithubApiViewState.GetUserRepos -> {
                viewState.list.forEach {
                    Log.d("결과", it.name)
                }
            }
        }
    }

}