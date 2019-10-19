package com.exercise.githubuser.data.network

import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.utils.network.NetworkService
import io.reactivex.Observable

class UserService {
    companion object {
        fun getUsers(since: Long, pageSize: Int): Observable<List<User>> {
            val userInfoApi = NetworkService.getRetrofit().create(UserApi::class.java)
            return userInfoApi.getUsers(since, pageSize)
        }
    }
}