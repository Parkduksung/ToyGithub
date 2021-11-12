package com.example.toygithub.ui.github

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.toygithub.R
import com.example.toygithub.base.BaseActivity
import com.example.toygithub.base.ViewState
import com.example.toygithub.databinding.ActivityMainBinding
import com.example.toygithub.ui.adapter.FragmentPagerAdapter
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

    private fun initUi() {
        val list = listOf(GithubApiFragment(), GithubLocalFragment())

        val pagerAdapter = FragmentPagerAdapter(list, this@MainActivity)

        with(binding) {
            viewpager.apply {
                adapter = pagerAdapter
                offscreenPageLimit = 2
            }
            TabLayoutMediator(tablayout, viewpager, tabConfigurationStrategy).attach()
        }
    }

    private fun initViewModel() {

        githubHomeViewModel.viewStateLiveData.observe(this) { viewState: ViewState? ->
            (viewState as? GithubHomeViewModel.GithubViewState)?.let {
                onChangedHomeViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedHomeViewState(viewState: GithubHomeViewModel.GithubViewState) {
        when (viewState) {

            is GithubHomeViewModel.GithubViewState.AddBookmark -> {
                Toast.makeText(this, "즐겨찾기가 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }

            is GithubHomeViewModel.GithubViewState.DeleteBookmark -> {
                Toast.makeText(this, "즐겨찾기가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
