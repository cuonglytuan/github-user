package com.exercise.githubuser.data.network

import com.exercise.githubuser.data.entity.UserDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserDetailApi {
    @Headers(
        "Accept: application/json;charset=utf-t",
        "Accept-Language: en"
    )
    @GET("users/{login}")
    fun getUserDetail(@Path("login") login: String): Observable<UserDetail>
}