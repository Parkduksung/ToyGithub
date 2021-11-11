package com.example.toygithub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.toygithub.api.response.SearchResponseItem
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    app: Application,
    private val githubRepository: GithubRepository
) : AndroidViewModel(app) {


    val inputUserLiveData = MutableLiveData<String>()

    private val _githubViewStateLiveData = MutableLiveData<GithubViewState>()
    val githubViewStateLiveData: LiveData<GithubViewState> = _githubViewStateLiveData

    fun searchUser() {

        viewModelScope.launch(Dispatchers.IO) {
            inputUserLiveData.value?.let { inputUser ->

                when (val result = githubRepository.searchUser(userId = inputUser)) {

                    is Result.Success -> {

                        viewModelScope.launch(Dispatchers.Main) {
                            if (!result.data.items.isNullOrEmpty()) {
                                _githubViewStateLiveData.value =
                                    GithubViewState.GetSearchUser(result.data.items.orEmpty())
                            } else {
                                _githubViewStateLiveData.value =
                                    GithubViewState.GetEmptyUser
                            }
                        }
                    }

                    is Result.Error -> {
                        viewModelScope.launch(Dispatchers.Main) {
                            _githubViewStateLiveData.value =
                                GithubViewState.ErrorSearch(result.exception.message.orEmpty())
                        }
                    }
                }
            }
        }
    }


    sealed class GithubViewState {
        data class GetSearchUser(val list: List<SearchResponseItem>) : GithubViewState()
        data class ErrorSearch(val message: String) : GithubViewState()
        object GetEmptyUser : GithubViewState()
    }

}