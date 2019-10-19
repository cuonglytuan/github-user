package com.exercise.githubuser.domain.repository

import android.content.Context
import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.data.entity.UserDetail
import com.exercise.githubuser.data.network.UserDetailService
import com.exercise.githubuser.data.network.UserService
import com.exercise.githubuser.ext.isNetworkConnected
import io.reactivex.Observable
import javax.inject.Inject

class UserDetailRepositoryImpl @Inject constructor(val context: Context) : UserDetailRepository {

    override fun getUserDetail(login: String): Observable<UserDetail> {
        return UserDetailService.getUserDetail(login)
    }

    override fun isOnline(): Boolean {
        return context.isNetworkConnected()
    }
}