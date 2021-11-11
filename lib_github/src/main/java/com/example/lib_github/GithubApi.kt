package com.example.lib_github

import com.example.lib_github.search.GithubSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GithubApi {

    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String,
        @Query("type") type: String = "user",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 0,
    ): Call<GithubSearchResponse>
}