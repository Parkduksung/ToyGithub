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
            if (githubRepository.isExistGithubEntity(entity)) {
                if (githubRepository.registerGithubEntity(entity.copy(like = true))) {
                    viewStateChanged(GithubViewState.AddBookmark(entity.copy(like = true)))
                } else {
                    viewStateChanged(GithubViewState.ErrorAddBookmark)
                }
            } else {
                if (githubRepository.registerGithubEntity(entity.copy(like = true))) {
                    viewStateChanged(GithubViewState.AddBookmark(entity.copy(like = true)))
                } else {
                    viewStateChanged(GithubViewState.ErrorAddBookmark)
                }
            }
        }
    }

    fun deleteBookmark(entity: GithubEntity) {
        ioScope.launch {
            if (githubRepository.deleteGithubEntity(entity)) {
                viewStateChanged(GithubViewState.DeleteBookmark(entity))
            } else {
                viewStateChanged(GithubViewState.ErrorDeleteBookmark)
            }
        }
    }




    sealed class GithubViewState : ViewState {
        data class AddBookmark(val item: GithubEntity) : GithubViewState()
        data class DeleteBookmark(val item: GithubEntity) : GithubViewState()
        object ErrorAddBookmark : GithubViewState()
        object ErrorDeleteBookmark : GithubViewState()
    }

}