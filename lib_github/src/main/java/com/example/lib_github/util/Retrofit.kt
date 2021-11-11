package com.example.lib_github.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    const val BASE_GITHUB_URL = "https://api.github.com/"

    inline fun <reified T> create(baseUrl: String = BASE_GITHUB_URL): T =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(T::class.java)
}