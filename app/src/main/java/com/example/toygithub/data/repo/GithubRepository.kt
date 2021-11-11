package com.example.toygithub.data.repo

import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.util.Result

interface GithubRepository {

    suspend fun searchUser(
        userId: String
    ): Result<GithubSearchResponse>

}