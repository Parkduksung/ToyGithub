package com.example.toygithub.room

import androidx.room.*

@Dao
interface GithubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerGithubEntity(excelList: GithubEntity): Long

    @Query("SELECT * FROM github_table")
    fun getAll(): List<GithubEntity>

    @Query("SELECT * FROM github_table WHERE `id` = (:id) ")
    fun getGithubEntity(id: Int): GithubEntity


    @Query("SELECT * FROM github_table WHERE `id` = (:id) ")
    fun isExistGithubEntity(id: Int): Long

    @Query("DELETE FROM github_table WHERE `id` = (:id)")
    fun deleteGithubEntity(id: Int): Int

    @Query("SELECT * FROM github_table WHERE `like` = (:like)")
    fun getBookmarkGithubEntityList(like: Boolean = true): List<GithubEntity>


}