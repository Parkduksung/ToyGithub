package com.example.toygithub.data.repo

import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.data.source.local.GithubLocalDataSource
import com.example.toygithub.data.source.remote.GithubRemoteDataSource
import com.example.toygithub.room.GithubEntity
import com.example.toygithub.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubRemoteDataSource: GithubRemoteDataSource,
    private val githubLocalDataSource: GithubLocalDataSource
) :
    GithubRepository {

    override suspend fun searchUser(userId: String): Result<GithubSearchResponse> =
        withContext(Dispatchers.IO) {
            return@withContext githubRemoteDataSource.searchUser(userId)
        }

    override suspend fun registerGithubEntity(entity: GithubEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext githubLocalDataSource.registerGithubEntity(entity)
        }

    override suspend fun isExistGithubEntityList(): Boolean = withContext(Dispatchers.IO) {
        return@withContext githubLocalDataSource.isExistGithubEntityList()
    }

    override suspend fun getAllGithubEntity(): Result<List<GithubEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext githubLocalDataSource.getAllGithubEntity()
        }

    override suspend fun deleteGithubEntity(entity: GithubEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext githubLocalDataSource.deleteGithubEntity(entity)
        }

    override suspend fun isExistGithubEntity(entity: GithubEntity): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext githubLocalDataSource.isExistGithubEntity(entity)
        }

    override suspend fun getAllBookmarkEntity(): Result<List<GithubEntity>> =
        withContext(Dispatchers.IO) {
            return@withContext githubLocalDataSource.getAllBookmarkEntity()
        }
}