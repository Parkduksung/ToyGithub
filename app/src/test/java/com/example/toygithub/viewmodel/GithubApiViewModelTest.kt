package com.example.toygithub.viewmodel

import base.ViewModelBaseTest
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.data.repo.GithubRepositoryImpl
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImplTest
import com.example.toygithub.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GithubApiViewModelTest : ViewModelBaseTest() {


    @Mock
    lateinit var githubRepository: GithubRepository

    private lateinit var githubApiViewModel: GithubApiViewModel


    @ExperimentalCoroutinesApi
    override fun setup() {
        super.setup()
        githubRepository = Mockito.mock(GithubRepositoryImpl::class.java)
        githubApiViewModel = GithubApiViewModel(application, githubRepository)
        githubApiViewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    @Test
    fun checkSearchUserSuccessTest() = runBlocking {

        val mockResponse = GithubRemoteDataSourceImplTest.mockGithubSearchResponse(true, 1)

        val successResult =
            Result.Success(mockResponse)

        Mockito.`when`(githubRepository.searchUser("Parkduksung")).thenReturn(successResult)

        mockResponse.items?.forEach {
            Mockito.`when`(githubRepository.isExistGithubEntity(it.toGithubEntity()))
                .thenReturn(true)
        }

        githubApiViewModel.inputUserLiveData.value = "Parkduksung"

        githubApiViewModel.searchUser()

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubApiViewModel.GithubApiViewState.GetSearchUser(mockResponse.items!!.map {
                it.toGithubEntity().copy(like = true)
            }))

    }

    @Test
    fun checkSearchUserFailTest() = runBlocking {

        val exception = Exception("Error GithubSearchResponse!")

        val failResult = Result.Error(exception)

        Mockito.`when`(githubRepository.searchUser("Parkduksung")).thenReturn(failResult)


        githubApiViewModel.inputUserLiveData.value = "Parkduksung"

        githubApiViewModel.searchUser()

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubApiViewModel.GithubApiViewState.ErrorSearch(exception.message.toString()))

    }

    @Test
    fun checkSearchUserEmptyTest() = runBlocking {

        val mockResponse =
            GithubRemoteDataSourceImplTest.mockGithubSearchResponse(true, 1, emptyList())

        val successResult =
            Result.Success(mockResponse)

        Mockito.`when`(githubRepository.searchUser("Parkduksung")).thenReturn(successResult)

        githubApiViewModel.inputUserLiveData.value = "Parkduksung"

        githubApiViewModel.searchUser()

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubApiViewModel.GithubApiViewState.GetEmptyUser)
    }

    @Test
    fun checkToggleBookmarkAddTest() = runBlocking {

        val mockGithubEntity = GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        githubApiViewModel.toggleBookmark(mockGithubEntity, isBookmark = true)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubApiViewModel.GithubApiViewState.AddBookmark(mockGithubEntity))

    }

    @Test
    fun checkToggleBookmarkDeleteTest() = runBlocking {

        val mockGithubEntity = GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        githubApiViewModel.toggleBookmark(mockGithubEntity, isBookmark = false)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubApiViewModel.GithubApiViewState.DeleteBookmark(mockGithubEntity))

    }
}