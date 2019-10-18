package com.exercise.githubuser.data.entity

import com.google.gson.annotations.SerializedName

data class UserDetail (
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("site_admin") val siteAdmin: Boolean,
    @SerializedName("location") val location: String,
    @SerializedName("blog") val blog: String
)