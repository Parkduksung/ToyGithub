package com.example.toygithub.api

import com.example.toygithub.api.response.GithubRepoResponse
import com.example.toygithub.api.response.GithubSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String,
        @Query("type") type: String = "user",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 0,
    ): Call<GithubSearchResponse>


    @GET("users/{userId}/repos")
    fun getUserRepos(
        @Path("userId") userId: String
    ) : Call<GithubRepoResponse>
}