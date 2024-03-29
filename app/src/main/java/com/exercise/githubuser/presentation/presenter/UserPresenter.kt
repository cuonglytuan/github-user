package com.exercise.githubuser.presentation.presenter

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.exercise.githubuser.domain.repository.UserRepository
import com.exercise.githubuser.presentation.contract.UserContract
import com.exercise.githubuser.utils.scheduler.BaseSchedulerProvider
import javax.inject.Inject

class UserPresenter @Inject constructor(
    private val mView: UserContract.View,
    private val mRepository: UserRepository,
    private val mSchedulerProvider: BaseSchedulerProvider
) : UserContract.Presenter {

    @VisibleForTesting(otherwise = PRIVATE)
    var isLoading = false
    @VisibleForTesting(otherwise = PRIVATE)
    var since: Long = 0
    @VisibleForTesting(otherwise = PRIVATE)
    var canLoadMore = true

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        mView.initView()
    }

    @SuppressLint("CheckResult")
    override fun getUser(startIndex: Long) {

        if (startIndex == 0L) {
            mView.showLoadingView()
            canLoadMore = true
            since = 0
        }

        // Offline
        if (!mRepository.isOnline()) {
            mView.hideLoadingView()
            mView.onUserFail()
            return
        }

        if (isLoading) {
            return
        }

        isLoading = true

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
                    if (since == 0L) {
                        mView.onUserEmpty()
                    }
                    canLoadMore = false
                }
                mView.hideLoadingView()
            }, {
                canLoadMore = false
                isLoading = false
                if (since == 0L) {
                    mView.onUserFail()
                }
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