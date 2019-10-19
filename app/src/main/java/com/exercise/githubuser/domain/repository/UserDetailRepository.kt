package com.exercise.githubuser.domain.repository

import com.exercise.githubuser.data.entity.UserDetail
import io.reactivex.Observable

interface UserDetailRepository {
    fun getUserDetail(login: String): Observable<UserDetail>
    fun isOnline(): Boolean
}