package com.example.lib_github.search

import com.example.lib_github.GithubApi
import com.example.lib_github.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GithubSearch(private val githubApi: GithubApi) {

    suspend fun getSearchUser(
        userId: String
    ): Result<GithubSearchResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(
                githubApi.getGithubUser(q = userId).execute().body()!!
            )
        } catch (e: Exception) {
            Result.Error(Exception("Error GithubSearchResponse!"))
        }
    }

}