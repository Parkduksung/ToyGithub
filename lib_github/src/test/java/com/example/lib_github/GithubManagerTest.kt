package com.example.lib_github

import com.example.lib_github.search.GithubSearchResponse
import com.example.lib_github.search.GithubSearchTest
import com.example.lib_github.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GithubManagerTest {


    internal lateinit var githubManager: GithubManager

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        githubManager = GithubManager()
    }


    @Test
    fun checkGetSearchUserSuccessTest() = runBlocking {

        val mockGithubSearchResponse =
            GithubSearchTest.mockGithubSearchResponse(incompleteResults = true, totalCount = 1)

        val successResult =
            Result.Success(mockGithubSearchResponse)

        MatcherAssert.assertThat(
            "올바른 GithubSearchResponse 값이 나오므로 성공",
            ((githubManager.getSearchUser(userId = "Parkduksung")
                    as Result.Success<GithubSearchResponse>).data.items?.get(0)?.login),
            Matchers.`is`(successResult.data.items?.get(0)?.login)
        )
    }

}