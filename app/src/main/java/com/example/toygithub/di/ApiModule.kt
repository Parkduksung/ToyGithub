package com.example.toygithub.di

import com.example.toygithub.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL_GITHUB = "https://api.github.com/"

    @Singleton
    @Provides
    fun provideGithubApi(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}