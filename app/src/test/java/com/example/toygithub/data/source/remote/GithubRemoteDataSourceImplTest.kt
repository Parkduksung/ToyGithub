package com.example.toygithub.data.source.remote

import base.BaseTest
import com.example.toygithub.api.GithubApi
import com.example.toygithub.api.response.GithubSearchResponse
import com.example.toygithub.api.response.SearchResponseItem
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.toygithub.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi


class GithubRemoteDataSourceImplTest : BaseTest() {

    @Mock
    lateinit var githubApi: GithubApi


    private lateinit var githubRemoteDataSourceImpl: GithubRemoteDataSourceImpl

    @ExperimentalCoroutinesApi
    override fun setup() {
        super.setup()
        githubApi = Mockito.mock(GithubApi::class.java)
        githubRemoteDataSourceImpl = GithubRemoteDataSourceImpl(githubApi)
    }

    @Test
    fun checkSearchUserSuccessTest() = runBlocking {

        val mockGithubSearchResponse =
            mockGithubSearchResponse(incompleteResults = true, totalCount = 1)

        initMockGithubSearchApi(mockGithubSearchResponse)

        val successResult =
            Result.Success(mockGithubSearchResponse)

        MatcherAssert.assertThat(
            "올바른 GithubSearchResponse 값이 나오므로 성공",
            ((githubRemoteDataSourceImpl.searchUser(userId = "Parkduksung") as Result.Success<GithubSearchResponse>).data),
            Matchers.`is`(successResult.data)
        )

    }

    @Test
    fun checkSearchUserFailTest() = runBlocking {

        val failResult = Result.Error(Exception("Error GithubSearchResponse!"))

        Mockito.`when`(githubApi.getGithubUser(q = "Parkduksung"))
            .then { failResult }

        MatcherAssert.assertThat(
            "Error 가 발생했으므로 실패.",
            ((githubRemoteDataSourceImpl.searchUser(userId = "Parkduksung") as Result.Error).exception.message),
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
            mockItems: List<SearchResponseItem> = mockSearchItemList
        ): GithubSearchResponse =
            GithubSearchResponse(
                incomplete_results = incompleteResults,
                total_count = totalCount,
                items = mockItems
            )

        val mockSearchItemList = listOf(
            SearchResponseItem(
                login = "Parkduksung"
            )
        )
    }

}