package com.example.toygithub.data.source.remote

import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.util.Result

interface GithubRemoteDataSource {

    suspend fun searchUser(
        userId: String
    ): Result<GithubSearchResponse>

}