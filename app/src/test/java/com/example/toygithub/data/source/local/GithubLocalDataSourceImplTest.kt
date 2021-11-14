package com.example.toygithub.data.source.local

import base.BaseTest
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImplTest.Companion.mockSearchItemList
import com.example.toygithub.room.GithubDao
import com.example.toygithub.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
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

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubDao.registerGithubEntity(mockGithubEntity)).thenReturn(1)

        MatcherAssert.assertThat(
            githubLocalDataSourceImpl.registerGithubEntity(mockGithubEntity),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkRegisterGithubEntityFailTest() = runBlocking {

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        val failureResult = Result.Error(Exception())

        Mockito.`when`(githubDao.registerGithubEntity(mockGithubEntity)).then { failureResult }

        MatcherAssert.assertThat(
            githubLocalDataSourceImpl.registerGithubEntity(mockGithubEntity),
            Matchers.`is`(false)
        )
    }

    @Test
    fun checkGetAllBookmarkEntitySuccessTest() = runBlocking {
        val mockGithubEntityList = mockSearchItemList.map { it.toGithubEntity().copy(like = true) }

        val successResult = Result.Success(mockGithubEntityList)

        Mockito.`when`(githubDao.getBookmarkGithubEntityList())
            .thenReturn(successResult.data)

        MatcherAssert.assertThat(
            (githubLocalDataSourceImpl.getAllBookmarkEntity() as Result.Success).data,
            Matchers.`is`(successResult.data)
        )

    }

    @Test
    fun checkGetAllBookmarkEntityFailTest() = runBlocking {
        val exception = Exception("BookmarkList is Null!")

        val failureResult = Result.Error(exception)

        Mockito.`when`(githubDao.getBookmarkGithubEntityList()).then { failureResult }

        MatcherAssert.assertThat(
            (githubLocalDataSourceImpl.getAllBookmarkEntity() as Result.Error).exception.message,
            Matchers.`is`(failureResult.exception.message)
        )
    }

    @Test
    fun checkDeleteGithubEntitySuccessTest() = runBlocking {

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity().copy(id = 1234)

        Mockito.`when`(
            githubDao.deleteGithubEntity(id = mockGithubEntity.id!!)
        ).thenReturn(1)

        MatcherAssert.assertThat(
            githubLocalDataSourceImpl.deleteGithubEntity(mockGithubEntity),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkDeleteGithubEntityFailTest() = runBlocking {


        val mockGithubEntity = mockSearchItemList[0].toGithubEntity().copy(id = 1234)

        val failureResult = Result.Error(Exception())

        Mockito.`when`(
            githubDao.deleteGithubEntity(id = mockGithubEntity.id!!)
        ).then { failureResult }

        MatcherAssert.assertThat(
            githubLocalDataSourceImpl.deleteGithubEntity(mockGithubEntity),
            Matchers.`is`(false)
        )

    }

    @Test
    fun checkIsExistGithubEntitySuccessTest() = runBlocking {

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity().copy(id = 1234)

        Mockito.`when`(githubDao.isExistGithubEntity(id = 1234))
            .thenReturn(1)

        MatcherAssert.assertThat(
            (githubLocalDataSourceImpl.isExistGithubEntity(mockGithubEntity)),
            Matchers.`is`(true)
        )

    }

    @Test
    fun checkIsExistGithubEntityFailTest() = runBlocking {

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity().copy(id = 1234)

        val failureResult = Result.Error(Exception())

        Mockito.`when`(githubDao.isExistGithubEntity(id = 1234)).then { failureResult }

        MatcherAssert.assertThat(
            (githubLocalDataSourceImpl.isExistGithubEntity(mockGithubEntity)),
            Matchers.`is`(false)
        )
    }

}