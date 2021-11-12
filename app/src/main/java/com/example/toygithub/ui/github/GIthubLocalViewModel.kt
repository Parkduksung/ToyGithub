package com.example.toygithub.ui.github

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.toygithub.base.BaseViewModel
import com.example.toygithub.base.ViewState
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.room.GithubEntity
import com.example.toygithub.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubLocalViewModel @Inject constructor(
    app: Application,
    private val githubRepository: GithubRepository
) : BaseViewModel(app), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getAllBookmarkList() {

        ioScope.launch {
            when (val result = githubRepository.getAllBookmarkEntity()) {
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        viewStateChanged(GithubLocalViewState.GetBookmarkList(result.data))
                    } else {
                        viewStateChanged(GithubLocalViewState.EmptyGetBookmarkList)
                    }
                }

                is Result.Error -> {
                    viewStateChanged(GithubLocalViewState.ErrorGetBookmarkList)
                }
            }
        }
    }


    fun deleteBookmark(entity: GithubEntity) {
        viewStateChanged(GithubLocalViewState.DeleteBookmark(entity))
    }

    sealed class GithubLocalViewState : ViewState {
        data class GetBookmarkList(val list: List<GithubEntity>) : GithubLocalViewState()
        data class DeleteBookmark(val item: GithubEntity) : GithubLocalViewState()
        object ErrorGetBookmarkList : GithubLocalViewState()
        object EmptyGetBookmarkList : GithubLocalViewState()
    }
}