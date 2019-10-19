package com.exercise.githubuser.domain.repository

import com.exercise.githubuser.data.entity.User
import io.reactivex.Observable

interface UserRepository {
    fun getUsers(since: Long, pageSize: Int = DEFAULT_PAGE_SIZE): Observable<List<User>>
    fun isOnline(): Boolean
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}