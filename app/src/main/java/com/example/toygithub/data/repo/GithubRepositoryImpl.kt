package com.example.toygithub.data.repo

import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.data.source.remote.GithubRemoteDataSource
import com.example.toygithub.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubRemoteDataSource: GithubRemoteDataSource) :
    GithubRepository {

    override suspend fun searchUser(userId: String): Result<GithubSearchResponse> =
        withContext(Dispatchers.IO) {
            return@withContext githubRemoteDataSource.searchUser(userId)
        }
}