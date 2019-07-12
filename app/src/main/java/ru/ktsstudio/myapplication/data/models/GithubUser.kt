package ru.ktsstudio.myapplication.data.models

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String,
    val id: Long,
    @SerializedName("node_id")
    val nodeId: String,
    val url: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("site_admin")
    val isAdmin: Boolean,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("starred_url")
    val starredUrl: String
)