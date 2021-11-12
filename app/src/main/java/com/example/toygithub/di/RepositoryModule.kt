package com.example.toygithub.di


import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.data.repo.GithubRepositoryImpl
import com.example.toygithub.data.source.local.GithubLocalDataSource
import com.example.toygithub.data.source.local.GithubLocalDataSourceImpl
import com.example.toygithub.data.source.remote.GithubRemoteDataSource
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository

    @Singleton
    @Binds
    abstract fun bindGithubRemoteDataSource(githubRemoteDataSourceImpl: GithubRemoteDataSourceImpl): GithubRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindGithubLocalDataSource(githubLocalDataSourceImpl: GithubLocalDataSourceImpl): GithubLocalDataSource


}

