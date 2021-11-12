package com.example.toygithub.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_table")
data class GithubEntity(
    @PrimaryKey(autoGenerate = true) val entityId: Int = 0,
    @ColumnInfo(name = "avatar_url") var avatar_url: String? = null,
    @ColumnInfo(name = "events_url") var events_url: String? = null,
    @ColumnInfo(name = "followers_url") var followers_url: String? = null,
    @ColumnInfo(name = "following_url") var following_url: String? = null,
    @ColumnInfo(name = "gists_url") var gists_url: String? = null,
    @ColumnInfo(name = "gravatar_id") var gravatar_id: String? = null,
    @ColumnInfo(name = "html_url") var html_url: String? = null,
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "login") var login: String? = null,
    @ColumnInfo(name = "node_id") var node_id: String? = null,
    @ColumnInfo(name = "organizations_url") var organizations_url: String? = null,
    @ColumnInfo(name = "received_events_url") var received_events_url: String? = null,
    @ColumnInfo(name = "repos_url") var repos_url: String? = null,
    @ColumnInfo(name = "score") var score: Double? = null,
    @ColumnInfo(name = "site_admin") var site_admin: Boolean? = null,
    @ColumnInfo(name = "starred_url") var starred_url: String? = null,
    @ColumnInfo(name = "subscriptions_url") var subscriptions_url: String? = null,
    @ColumnInfo(name = "type") var type: String? = null,
    @ColumnInfo(name = "url") var url: String? = null,
    @ColumnInfo(name = "like") var like: Boolean? = null,
)
