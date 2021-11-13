package com.example.toygithub.data.source.local

import com.example.toygithub.room.GithubEntity
import com.example.toygithub.util.Result

interface GithubLocalDataSource {

    suspend fun registerGithubEntity(entity: GithubEntity): Boolean

    suspend fun getAllBookmarkEntity(): Result<List<GithubEntity>>

    suspend fun deleteGithubEntity(entity: GithubEntity): Boolean

    suspend fun isExistGithubEntity(entity: GithubEntity): Boolean
}