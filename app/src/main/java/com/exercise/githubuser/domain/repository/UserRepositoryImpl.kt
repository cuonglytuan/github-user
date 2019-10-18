package com.exercise.githubuser.domain.repository

import android.content.Context
import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.data.network.UserService
import com.exercise.githubuser.ext.isNetworkConnected
import io.reactivex.Observable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(val context: Context) : UserRepository {

    override fun getSofUsers(since: Long, pageSize: Int): Observable<List<User>> {
        return UserService.getUsers(since, pageSize)
    }

    override fun isOnline(): Boolean {
        return context.isNetworkConnected()
    }
}