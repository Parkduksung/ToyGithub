package com.example.toygithub.data.source.remote

import com.example.toygithub.api.GithubApi
import com.example.toygithub.api.response.GithubRepoResponse
import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(private val githubApi: GithubApi) :
    GithubRemoteDataSource {

    override suspend fun searchUser(userId: String): Result<GithubSearchResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(githubApi.getGithubUser(q = userId).execute().body()!!)
            } catch (e: Exception) {
                Result.Error(Exception("Error GithubSearchResponse!"))
            }
        }

    override suspend fun searchUserRepos(userId: String): Result<GithubRepoResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(githubApi.getUserRepos(userId = userId).execute().body()!!)
            } catch (e: Exception) {
                Result.Error(Exception("Error getUserRepos!"))
            }
        }
}