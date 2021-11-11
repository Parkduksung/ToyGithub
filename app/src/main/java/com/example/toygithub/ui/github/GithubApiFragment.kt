package com.example.toygithub.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.toygithub.R
import com.example.toygithub.databinding.FragmentGithubApiBinding
import com.example.toygithub.ui.GithubViewModel
import com.example.toygithub.ui.adapter.ApiAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GithubApiFragment : Fragment() {

    private val githubViewModel by activityViewModels<GithubViewModel>()

    private lateinit var binding: FragmentGithubApiBinding

    private val apiAdapter by lazy { ApiAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_github_api, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = githubViewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }


    private fun initUi() {

        binding.rvApi.run {
            adapter = apiAdapter
        }
        apiAdapter.clear()
    }

    private fun initViewModel() {

        githubViewModel.githubViewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is GithubViewModel.GithubViewState.GetSearchUser -> {
                    apiAdapter.addAll(viewState.list)
                }
            }
        }
    }

}