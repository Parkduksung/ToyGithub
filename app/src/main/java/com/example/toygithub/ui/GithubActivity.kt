package com.example.toygithub.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.toygithub.R
import com.example.toygithub.databinding.ActivityMainBinding
import com.example.toygithub.ui.github.GithubApiFragment
import com.example.toygithub.ui.github.GithubLocalFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val githubViewModel by viewModels<GithubViewModel>()

    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_tab_name)[position]
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

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
}

class FragmentPagerAdapter(
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]

}