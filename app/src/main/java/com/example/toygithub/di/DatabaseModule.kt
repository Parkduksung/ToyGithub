package com.example.toygithub.di

import android.content.Context
import androidx.room.Room
import com.example.toygithub.room.GithubDao
import com.example.toygithub.room.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideGithubDao(githubDatabase: GithubDatabase): GithubDao {
        return githubDatabase.githubDao()
    }

    @Provides
    @Singleton
    fun provideGithubDatabase(@ApplicationContext appContext: Context): GithubDatabase {
        return Room.databaseBuilder(
            appContext,
            GithubDatabase::class.java,
            "github_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}