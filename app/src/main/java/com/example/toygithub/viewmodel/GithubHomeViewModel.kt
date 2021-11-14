package com.example.toygithub.viewmodel

import android.app.Application
import com.example.toygithub.base.BaseViewModel
import com.example.toygithub.base.ViewState
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.room.GithubEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubHomeViewModel @Inject constructor(
    app: Application,
    private val githubRepository: GithubRepository
) : BaseViewModel(app) {

    fun addBookmark(entity: GithubEntity) {
        ioScope.launch {
            if (githubRepository.registerGithubEntity(entity.copy(like = true))) {
                viewStateChanged(GithubHomeViewState.AddBookmark(entity.copy(like = true)))
            } else {
                viewStateChanged(GithubHomeViewState.ErrorAddBookmark)
            }
        }
    }

    fun deleteBookmark(entity: GithubEntity) {
        ioScope.launch {
            if (githubRepository.deleteGithubEntity(entity)) {
                viewStateChanged(GithubHomeViewState.DeleteBookmark(entity))
            } else {
                viewStateChanged(GithubHomeViewState.ErrorDeleteBookmark)
            }
        }
    }


    sealed class GithubHomeViewState : ViewState {
        data class AddBookmark(val item: GithubEntity) : GithubHomeViewState()
        data class DeleteBookmark(val item: GithubEntity) : GithubHomeViewState()
        object ErrorAddBookmark : GithubHomeViewState()
        object ErrorDeleteBookmark : GithubHomeViewState()
    }

}