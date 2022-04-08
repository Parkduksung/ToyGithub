package com.example.toygithub.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.toygithub.api.response.GithubRepoResponseItem
import com.example.toygithub.base.BaseViewModel
import com.example.toygithub.base.ViewState
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.room.GithubEntity
import com.example.toygithub.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubApiViewModel @Inject constructor(
    app: Application,
    private val githubRepository: GithubRepository
) : BaseViewModel(app) {

    val inputUserLiveData = MutableLiveData<String>()

    fun searchUser() {
        ioScope.launch {
            inputUserLiveData.value?.let { inputUser ->
                when (val result = githubRepository.searchUser(userId = inputUser)) {
                    is Result.Success -> {
                        viewModelScope.launch(Dispatchers.Main) {

                            val searchList = result.data.items

                            if (!searchList.isNullOrEmpty()) {
                                val toGithubEntityList = searchList.map {
                                    it.toGithubEntity()
                                }
                                val toBookmarkGithubEntityList = toGithubEntityList.map {
                                    if (githubRepository.isExistGithubEntity(it)) {
                                        it.copy(like = true)
                                    } else {
                                        it.copy(like = false)
                                    }
                                }

                                viewStateChanged(
                                    GithubApiViewState.GetSearchUser(
                                        toBookmarkGithubEntityList
                                    )
                                )
                            } else {
                                viewStateChanged(GithubApiViewState.GetEmptyUser)
                            }
                        }
                    }
                    is Result.Error -> {
                        viewStateChanged(GithubApiViewState.ErrorSearch(result.exception.message.orEmpty()))
                    }
                }
            }
        }
    }

    fun toggleBookmark(entity: GithubEntity, isBookmark: Boolean) {
        if (isBookmark) {
            viewStateChanged(GithubApiViewState.AddBookmark(entity))
        } else {
            viewStateChanged(GithubApiViewState.DeleteBookmark(entity))
        }
    }

    fun searchUserRepos(userId: String) {
        ioScope.launch {
            when (val result = githubRepository.searchUserRepos(userId)) {
                is Result.Success -> {
                    viewStateChanged(
                        GithubApiViewState.GetUserRepos(
                            result
                                .data.toList()
                        )
                    )
                }

                is Result.Error -> {

                }
            }
        }
    }


    sealed class GithubApiViewState : ViewState {
        data class AddBookmark(val item: GithubEntity) : GithubApiViewState()
        data class DeleteBookmark(val item: GithubEntity) : GithubApiViewState()
        data class GetSearchUser(val list: List<GithubEntity>) : GithubApiViewState()
        data class GetUserRepos(val list: List<GithubRepoResponseItem>) : GithubApiViewState()
        data class ErrorSearch(val message: String) : GithubApiViewState()
        object GetEmptyUser : GithubApiViewState()
    }

}