package com.example.toygithub.data.repo

import base.BaseTest
import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.data.source.local.GithubLocalDataSource
import com.example.toygithub.data.source.local.GithubLocalDataSourceImpl
import com.example.toygithub.data.source.remote.GithubRemoteDataSource
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImpl
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImplTest
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImplTest.Companion.mockSearchItemList
import com.example.toygithub.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GithubRepositoryImplTest : BaseTest() {

    @Mock
    lateinit var githubLocalDataSource: GithubLocalDataSource

    @Mock
    lateinit var githubRemoteDataSource: GithubRemoteDataSource

    private lateinit var githubRepositoryImpl: GithubRepositoryImpl

    @ExperimentalCoroutinesApi
    override fun setup() {
        super.setup()
        githubLocalDataSource = Mockito.mock(GithubLocalDataSourceImpl::class.java)
        githubRemoteDataSource = Mockito.mock(GithubRemoteDataSourceImpl::class.java)
        githubRepositoryImpl = GithubRepositoryImpl(githubRemoteDataSource, githubLocalDataSource)
    }

    @Test
    fun checkGetSearchUserSuccessTest() = runBlocking {

        val successResult =
            Result.Success(GithubRemoteDataSourceImplTest.mockGithubSearchResponse(true, 1))


        Mockito.`when`(githubRemoteDataSource.searchUser("Parkduksung"))
            .thenReturn(successResult)

        MatcherAssert.assertThat(
            "올바른 Response 값이 전달받았으므로 성공",
            ((githubRepositoryImpl.searchUser("Parkduksung") as Result.Success<GithubSearchResponse>).data),
            Matchers.`is`(successResult.data)
        )


    }

    @Test
    fun checkGetSearchUserFailTest() = runBlocking {

        val failResult = Result.Error(Exception("Error GithubSearchResponse!"))

        Mockito.`when`(githubRemoteDataSource.searchUser("Parkduksung"))
            .then { failResult }

        MatcherAssert.assertThat(
            "Error 가 발생했으므로 실패.",
            ((githubRepositoryImpl.searchUser("Parkduksung") as Result.Error).exception.message),
            Matchers.`is`(failResult.exception.message)
        )
    }

    @Test
    fun checkGetRegisterGithubEntitySuccessTest() = runBlocking {

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubLocalDataSource.registerGithubEntity(mockGithubEntity))
            .thenReturn(true)

        MatcherAssert.assertThat(
            githubRepositoryImpl.registerGithubEntity(mockGithubEntity),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkGetRegisterGithubEntityFailTest() = runBlocking {

        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubLocalDataSource.registerGithubEntity(mockGithubEntity))
            .thenReturn(false)

        MatcherAssert.assertThat(
            githubRepositoryImpl.registerGithubEntity(mockGithubEntity),
            Matchers.`is`(false)
        )
    }


    @Test
    fun checkGetAllBookmarkEntitySuccessTest() = runBlocking {

        val mockGithubEntityList = mockSearchItemList.map { it.toGithubEntity().copy(like = true) }

        val successResult = Result.Success(mockGithubEntityList)

        Mockito.`when`(githubLocalDataSource.getAllBookmarkEntity()).thenReturn(successResult)

        MatcherAssert.assertThat(
            (githubRepositoryImpl.getAllBookmarkEntity() as Result.Success).data,
            Matchers.`is`(successResult.data)
        )
    }

    @Test
    fun checkGetAllBookmarkEntityFailTest() = runBlocking {
        val exception =
            Exception("BookmarkList is Null!")

        val failureResult = Result.Error(exception)

        Mockito.`when`(githubLocalDataSource.getAllBookmarkEntity()).then { failureResult }

        MatcherAssert.assertThat(
            (githubRepositoryImpl.getAllBookmarkEntity() as Result.Error).exception.message,
            Matchers.`is`(failureResult.exception.message)
        )
    }

    @Test
    fun checkDeleteGithubEntitySuccessTest() = runBlocking {
        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubLocalDataSource.deleteGithubEntity(mockGithubEntity)).thenReturn(true)

        MatcherAssert.assertThat(
            githubRepositoryImpl.deleteGithubEntity(mockGithubEntity),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkDeleteGithubEntityFailTest() = runBlocking {
        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubLocalDataSource.deleteGithubEntity(mockGithubEntity)).thenReturn(false)

        MatcherAssert.assertThat(
            githubRepositoryImpl.deleteGithubEntity(mockGithubEntity),
            Matchers.`is`(false)
        )
    }

    @Test
    fun checkIsExistGithubEntitySuccessTest() = runBlocking {
        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubLocalDataSource.isExistGithubEntity(mockGithubEntity)).thenReturn(true)

        MatcherAssert.assertThat(
            githubRepositoryImpl.isExistGithubEntity(mockGithubEntity),
            Matchers.`is`(true)
        )
    }

    @Test
    fun checkIsExistGithubEntityFailTest() = runBlocking {
        val mockGithubEntity = mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubLocalDataSource.isExistGithubEntity(mockGithubEntity))
            .thenReturn(false)

        MatcherAssert.assertThat(
            githubRepositoryImpl.isExistGithubEntity(mockGithubEntity),
            Matchers.`is`(false)
        )
    }


}