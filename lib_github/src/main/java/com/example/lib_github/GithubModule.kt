package com.example.lib_github

import com.example.lib_github.search.GithubSearchResponse
import com.example.lib_github.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubModule {

    private var githubManager: GithubManager? = null

    fun initialize() {
        githubManager = GithubManager()
    }

    fun release() {
        githubManager = null
    }

    suspend fun searchUser(
        searchId: String
    ): Result<GithubSearchResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            githubManager?.getSearchUser(userId = searchId)!!
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}