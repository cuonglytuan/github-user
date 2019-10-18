package com.exercise.githubuser.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("site_admin") val siteAdmin: Boolean,
    @SerializedName("id") val id: Long
)