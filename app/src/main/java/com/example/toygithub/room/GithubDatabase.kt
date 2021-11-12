package com.example.toygithub.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GithubEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun githubDao(): GithubDao
}