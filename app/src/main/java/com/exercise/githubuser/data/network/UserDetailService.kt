package com.exercise.githubuser.data.network

import com.exercise.githubuser.data.entity.UserDetail
import com.exercise.githubuser.utils.network.NetworkService
import io.reactivex.Observable

class UserDetailService {
    companion object {
        fun getUserDetail(login: String): Observable<UserDetail> {
            val userDetailApi = NetworkService.getRetrofit().create(UserDetailApi::class.java)
            return userDetailApi.getUserDetail(login)
        }
    }
}