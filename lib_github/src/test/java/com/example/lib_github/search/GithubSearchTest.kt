package com.example.lib_github.search

import com.example.lib_github.GithubApi
import com.example.lib_github.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.Request
import okio.Timeout
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class GithubSearchTest {

    @Mock
    internal lateinit var githubApi: GithubApi

    internal lateinit var githubSearch: GithubSearch

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        githubApi = Mockito.mock(GithubApi::class.java)
        githubSearch = GithubSearch(githubApi)
    }


    @Test
    fun checkGetGithubSearchResponseSuccessTest() = runBlocking {

        val mockGithubSearchResponse =
            mockGithubSearchResponse(incompleteResults = true, totalCount = 1)

        initMockGithubSearchApi(mockGithubSearchResponse)

        val successResult =
            Result.Success(mockGithubSearchResponse)

        MatcherAssert.assertThat(
            "올바른 GithubSearchResponse 값이 나오므로 성공",
            ((githubSearch.getSearchUser("Parkduksung") as Result.Success<GithubSearchResponse>).data),
            Matchers.`is`(successResult.data)
        )
    }

    @Test
    fun checkGetGithubSearchResponseFailTest() = runBlocking {

        val failResult = Result.Error(Exception("Error GithubSearchResponse!"))

        Mockito.`when`(githubApi.getGithubUser(q = "Parkduksung"))
            .then { failResult }

        MatcherAssert.assertThat(
            "Error 가 발생했으므로 실패.",
            ((githubSearch.getSearchUser(userId = "Parkduksung") as Result.Error).exception.message),
            Matchers.`is`(failResult.exception.message)
        )

    }


    fun initMockGithubSearchApi(response: GithubSearchResponse) {
        Mockito.`when`(githubApi.getGithubUser(q = "Parkduksung")).thenReturn(
            object : Call<GithubSearchResponse> {
                override fun clone(): Call<GithubSearchResponse> {
                    TODO("Not yet implemented")
                }

                override fun execute(): Response<GithubSearchResponse> {
                    return Response.success(response)
                }

                override fun enqueue(callback: Callback<GithubSearchResponse>) {
                    TODO("Not yet implemented")
                }

                override fun isExecuted(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun isCanceled(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun request(): Request {
                    TODO("Not yet implemented")
                }

                override fun timeout(): Timeout {
                    TODO("Not yet implemented")
                }
            }
        )
    }

    companion object {

        fun mockGithubSearchResponse(
            incompleteResults: Boolean,
            totalCount: Int,
            mockItems: List<SearchItem> = mockSearchItemList
        ): GithubSearchResponse =
            GithubSearchResponse(
                incomplete_results = incompleteResults,
                total_count = totalCount,
                items = mockItems
            )

        val mockSearchItemList = listOf(
            SearchItem(
                login = "Parkduksung"
            )
        )
    }

}