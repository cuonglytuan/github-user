package com.exercise.githubuser.presentation.presenter

import android.annotation.SuppressLint
import com.exercise.githubuser.domain.repository.UserRepository
import com.exercise.githubuser.presentation.contract.UserContract
import com.exercise.githubuser.utils.scheduler.BaseSchedulerProvider
import javax.inject.Inject

class UserPresenter @Inject constructor(
    private val mView: UserContract.View,
    private val mRepository: UserRepository,
    private val mSchedulerProvider: BaseSchedulerProvider
) : UserContract.Presenter {

    private var isLoading = false
    private var since: Long = 0
    private var canLoadMore = true

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        mView.initView()
    }

    @SuppressLint("CheckResult")
    override fun getUser(startIndex: Long) {
        // Offline
        if (!mRepository.isOnline()) {
            return
        }

        if (isLoading) {
            return
        }

        isLoading = true

        if (startIndex == 0L) {
            mView.showLoadingView()
            canLoadMore = true
            since = 0
        }

        mRepository.getUsers(startIndex, 20)
            .subscribeOn(mSchedulerProvider.io())
            .observeOn(mSchedulerProvider.ui())
            .subscribe({ listUser ->
                isLoading = false
                if (!listUser.isNullOrEmpty() && since != listUser.last().id) {
                    mView.onUserLoaded(listUser)
                    since = listUser.last().id
                    canLoadMore = true
                } else {
                    canLoadMore = false
                }
                mView.hideLoadingView()
            }, {
                mView.hideLoadingView()
            })
    }

    override fun getUserNext() {
        if (!canLoadMore) {
            return
        }
        getUser(since)
    }
}