package com.example.toygithub.ui.github

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.example.toygithub.R
import com.example.toygithub.base.BaseActivity
import com.example.toygithub.base.ViewState
import com.example.toygithub.databinding.ActivityMainBinding
import com.example.toygithub.ext.showToast
import com.example.toygithub.ui.adapter.FragmentPagerAdapter
import com.example.toygithub.viewmodel.GithubHomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val githubHomeViewModel by viewModels<GithubHomeViewModel>()

    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_tab_name)[position]
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initViewModel()
    }


    @SuppressLint("WrongConstant")
    private fun initUi() {
        val list = listOf(GithubApiFragment(), GithubLocalFragment())

        val pagerAdapter = FragmentPagerAdapter(list, this@MainActivity)

        with(binding) {
            viewpager.apply {
                adapter = pagerAdapter
                offscreenPageLimit = PAGE_LIMIT_COUNT
            }
            TabLayoutMediator(tablayout, viewpager, tabConfigurationStrategy).attach()
        }
    }

    private fun initViewModel() {

        githubHomeViewModel.viewStateLiveData.observe(this) { viewState: ViewState? ->
            (viewState as? GithubHomeViewModel.GithubHomeViewState)?.let {
                onChangedHomeViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedHomeViewState(viewState: GithubHomeViewModel.GithubHomeViewState) {
        when (viewState) {

            is GithubHomeViewModel.GithubHomeViewState.AddBookmark -> {
                showToast(message = "즐겨찾기가 추가되었습니다.")
            }

            is GithubHomeViewModel.GithubHomeViewState.DeleteBookmark -> {
                showToast(message = "즐겨찾기가 삭제되었습니다.")
            }

        }
    }

    companion object {
        private const val PAGE_LIMIT_COUNT = 2
    }
}
