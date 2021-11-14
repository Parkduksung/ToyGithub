package com.example.toygithub.viewmodel

import base.ViewModelBaseTest
import com.example.toygithub.data.repo.GithubRepository
import com.example.toygithub.data.repo.GithubRepositoryImpl
import com.example.toygithub.data.source.remote.GithubRemoteDataSourceImplTest
import com.example.toygithub.room.GithubEntity
import com.example.toygithub.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GithubLocalViewModelTest : ViewModelBaseTest() {

    @Mock
    lateinit var githubRepository: GithubRepository

    private lateinit var githubLocalViewModel: GithubLocalViewModel


    @ExperimentalCoroutinesApi
    override fun setup() {
        super.setup()
        githubRepository = Mockito.mock(GithubRepositoryImpl::class.java)
        githubLocalViewModel = GithubLocalViewModel(application, githubRepository)
        githubLocalViewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    @Test
    fun checkGetAllBookmarkSuccessTest() = runBlocking {

        val mockGithubEntityList = GithubRemoteDataSourceImplTest.mockSearchItemList.map {
            it.toGithubEntity().copy(like = true)
        }

        val successResult = Result.Success(mockGithubEntityList)

        Mockito.`when`(githubRepository.getAllBookmarkEntity()).thenReturn(successResult)

        githubLocalViewModel.getAllBookmarkList()

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(
                GithubLocalViewModel.GithubLocalViewState.GetBookmarkList(
                    successResult.data
                )
            )

    }

    @Test
    fun checkGetAllBookmarkFailTest() = runBlocking {

        val exception =
            Exception("BookmarkList is Null!")

        val failureResult = Result.Error(exception)

        Mockito.`when`(githubRepository.getAllBookmarkEntity()).then { failureResult }

        githubLocalViewModel.getAllBookmarkList()

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(
                GithubLocalViewModel.GithubLocalViewState.ErrorGetBookmarkList
            )
    }


    @Test
    fun checkGetAllBookmarkEmptyTest() = runBlocking {

        val mockEmptyGithubEntityList = emptyList<GithubEntity>()

        val successResult = Result.Success(mockEmptyGithubEntityList)

        Mockito.`when`(githubRepository.getAllBookmarkEntity()).thenReturn(successResult)

        githubLocalViewModel.getAllBookmarkList()

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(
                GithubLocalViewModel.GithubLocalViewState.EmptyGetBookmarkList
            )

    }


    @Test
    fun checkDeleteBookmarkTest() = runBlocking {

        val mockGithubEntity = GithubRemoteDataSourceImplTest.mockSearchItemList[0].toGithubEntity()

        githubLocalViewModel.deleteBookmark(mockGithubEntity)

        delay(100L)

        Mockito.verify(viewStateObserver)
            .onChanged(
                GithubLocalViewModel.GithubLocalViewState.DeleteBookmark(mockGithubEntity)
            )

    }
}