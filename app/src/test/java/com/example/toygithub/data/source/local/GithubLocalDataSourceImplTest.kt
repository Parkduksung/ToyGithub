package com.example.toygithub.data.source.local

import base.BaseTest
import com.example.toygithub.room.GithubDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GithubLocalDataSourceImplTest : BaseTest() {


    @Mock
    lateinit var githubDao: GithubDao

    private lateinit var githubLocalDataSourceImpl: GithubLocalDataSourceImpl

    @ExperimentalCoroutinesApi
    override fun setup() {
        super.setup()
        githubDao = Mockito.mock(GithubDao::class.java)
        githubLocalDataSourceImpl = GithubLocalDataSourceImpl(githubDao)
    }

    @Test
    fun checkRegisterGithubEntitySuccessTest() = runBlocking {

    }

    @Test
    fun checkRegisterGithubEntityFailTest() = runBlocking {

    }

    @Test
    fun checkGetAllBookmarkEntitySuccessTest() = runBlocking {

    }

    @Test
    fun checkGetAllBookmarkEntityFailTest() = runBlocking {

    }

    @Test
    fun checkDeleteGithubEntitySuccessTest() = runBlocking {

    }

    @Test
    fun checkDeleteGithubEntityFailTest() = runBlocking {

    }

    @Test
    fun checkIsExistGithubEntitySuccessTest() = runBlocking {

    }

    @Test
    fun checkIsExistGithubEntityFailTest() = runBlocking {

    }


}