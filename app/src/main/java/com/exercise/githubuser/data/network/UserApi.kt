package com.exercise.githubuser.data.network

import com.exercise.githubuser.data.entity.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UserApi {
    @Headers(
        "Accept: application/json;charset=utf-t",
        "Accept-Language: en"
    )
    @GET("users?")
    fun getUsers(
        @Query("since") since: Long,
        @Query("per_page") pageSize: Int
    ): Observable<List<User>>
}