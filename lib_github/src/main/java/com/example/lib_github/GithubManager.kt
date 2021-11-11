package com.example.lib_github

import com.example.lib_github.search.GithubSearch
import com.example.lib_github.search.GithubSearchResponse
import com.example.lib_github.util.Result
import com.example.lib_github.util.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GithubManager {

    private val githubSearch by lazy { GithubSearch(Retrofit.create()) }

    suspend fun getSearchUser(userId: String): Result<GithubSearchResponse> =
        withContext(Dispatchers.IO) {
            return@withContext githubSearch.getSearchUser(userId)
        }

}