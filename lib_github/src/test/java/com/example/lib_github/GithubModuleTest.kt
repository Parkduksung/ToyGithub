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
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GithubModuleTest {


    private lateinit var githubModule: GithubModule

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        githubModule = GithubModule()
    }


    @Test
    fun checkNotInitTest() = runBlocking {

        MatcherAssert.assertThat(
            "초기화 되지 않아 NullPointerException 발생.",
            ((githubModule.searchUser(searchId = "Parkduksung")) as Result.Error).exception.javaClass.name,
            Matchers.`is`(NullPointerException().javaClass.name)
        )
    }

    @Test
    fun checkGetSearchUserSuccessTest() = runBlocking {

        githubModule.initialize()

        val mockGithubSearchResponse =
            GithubSearchTest.mockGithubSearchResponse(incompleteResults = true, totalCount = 1)

        val successResult =
            Result.Success(mockGithubSearchResponse)

        MatcherAssert.assertThat(
            "올바른 GithubSearchResponse 값이 나오므로 성공",
            ((githubModule.searchUser(searchId = "Parkduksung")
                    as Result.Success<GithubSearchResponse>).data.items?.get(0)?.login),
            Matchers.`is`(successResult.data.items?.get(0)?.login)
        )
    }

    @After
    fun tearDown() {
        githubModule.release()
    }
}