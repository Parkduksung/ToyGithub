package com.example.toygithub.viewmodel

import base.ViewModelBaseTest
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.data.repo.GithubRepositoryImpl
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImplTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GithubHomeViewModelTest : ViewModelBaseTest() {

    @Mock
    lateinit var githubRepository: GithubRepository

    private lateinit var githubHomeViewModel: GithubHomeViewModel


    @ExperimentalCoroutinesApi
    override fun setup() {
        super.setup()
        githubRepository = Mockito.mock(GithubRepositoryImpl::class.java)
        githubHomeViewModel = GithubHomeViewModel(application, githubRepository)
        githubHomeViewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    @Test
    fun checkAddBookmarkSuccessTest() = runBlocking {

        val mockGithubEntity =
            GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubRepository.registerGithubEntity(mockGithubEntity.copy(like = true)))
            .thenReturn(true)

        githubHomeViewModel.addBookmark(mockGithubEntity)

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(
                GithubHomeViewModel.GithubHomeViewState.AddBookmark(mockGithubEntity.copy(like = true))
            )

    }

    @Test
    fun checkAddBookmarkFailTest() = runBlocking {
        val mockGithubEntity =
            GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubRepository.registerGithubEntity(mockGithubEntity.copy(like = true)))
            .thenReturn(false)

        githubHomeViewModel.addBookmark(mockGithubEntity)

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubHomeViewModel.GithubHomeViewState.ErrorAddBookmark)
    }

    @Test
    fun checkDeleteBookmarkSuccessTest() = runBlocking {
        val mockGithubEntity =
            GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubRepository.deleteGithubEntity(mockGithubEntity))
            .thenReturn(true)

        githubHomeViewModel.deleteBookmark(mockGithubEntity)

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(
                GithubHomeViewModel.GithubHomeViewState.DeleteBookmark(mockGithubEntity))


    }

    @Test
    fun checkDeleteBookmarkFailTest() = runBlocking {

        val mockGithubEntity =
            GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        Mockito.`when`(githubRepository.deleteGithubEntity(mockGithubEntity))
            .thenReturn(false)

        githubHomeViewModel.deleteBookmark(mockGithubEntity)

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(GithubHomeViewModel.GithubHomeViewState.ErrorDeleteBookmark)
    }

}