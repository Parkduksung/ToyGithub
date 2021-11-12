package com.example.toygithub.api.response

import com.example.toygithub.room.GithubEntity
import com.google.gson.annotations.SerializedName


data class GithubSearchResponse(
    @SerializedName("incomplete_results") val incomplete_results: Boolean? = null,
    @SerializedName("items") var items: List<SearchResponseItem>? = emptyList(),
    @SerializedName("total_count") val total_count: Int? = null
)

data class SearchResponseItem(
    @SerializedName("avatar_url") var avatar_url: String? = null,
    @SerializedName("events_url") var events_url: String? = null,
    @SerializedName("followers_url") var followers_url: String? = null,
    @SerializedName("following_url") var following_url: String? = null,
    @SerializedName("gists_url") var gists_url: String? = null,
    @SerializedName("gravatar_id") var gravatar_id: String? = null,
    @SerializedName("html_url") var html_url: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("login") var login: String? = null,
    @SerializedName("node_id") var node_id: String? = null,
    @SerializedName("organizations_url") var organizations_url: String? = null,
    @SerializedName("received_events_url") var received_events_url: String? = null,
    @SerializedName("repos_url") var repos_url: String? = null,
    @SerializedName("score") var score: Double? = null,
    @SerializedName("site_admin") var site_admin: Boolean? = null,
    @SerializedName("starred_url") var starred_url: String? = null,
    @SerializedName("subscriptions_url") var subscriptions_url: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("url") var url: String? = null
) {
    fun toGithubEntity(): GithubEntity =
        GithubEntity(
            avatar_url = avatar_url,
            events_url = events_url,
            followers_url = followers_url,
            following_url = following_url,
            gists_url = gists_url,
            gravatar_id = gravatar_id,
            html_url = html_url,
            id = id,
            login = login,
            node_id = node_id,
            organizations_url = organizations_url,
            received_events_url = received_events_url,
            repos_url = repos_url,
            score = score,
            site_admin = site_admin,
            starred_url = starred_url,
            subscriptions_url = subscriptions_url,
            type = type,
            url = url
        )
}