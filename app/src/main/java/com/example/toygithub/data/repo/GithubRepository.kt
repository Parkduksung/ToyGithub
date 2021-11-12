package com.example.toygithub.data.repo

import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.room.GithubEntity
import com.example.toygithub.util.Result

interface GithubRepository {

    suspend fun searchUser(
        userId: String
    ): Result<GithubSearchResponse>


    suspend fun registerGithubEntity(entity: GithubEntity): Boolean

    suspend fun isExistGithubEntityList(): Boolean

    suspend fun getAllGithubEntity(): Result<List<GithubEntity>>

    suspend fun getAllBookmarkEntity(): Result<List<GithubEntity>>

    suspend fun deleteGithubEntity(entity: GithubEntity): Boolean

    suspend fun isExistGithubEntity(entity: GithubEntity): Boolean

}