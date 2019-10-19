package com.exercise.githubuser.presentation.presenter

import android.annotation.SuppressLint
import com.exercise.githubuser.domain.repository.UserDetailRepository
import com.exercise.githubuser.presentation.contract.UserDetailContract
import com.exercise.githubuser.utils.scheduler.BaseSchedulerProvider
import javax.inject.Inject

class UserDetailPresenter @Inject constructor(
    private val mView: UserDetailContract.View,
    private val mRepository: UserDetailRepository,
    private val mSchedulerProvider: BaseSchedulerProvider
) : UserDetailContract.Presenter {

    private var isLoading = false

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        mView.initView()
    }

    @SuppressLint("CheckResult")
    override fun getUserDetail(login: String) {
        // Offline
        if (!mRepository.isOnline()) {
            return
        }

        if (isLoading) {
            return
        }

        isLoading = true

        mView.showLoadingView()

        mRepository.getUserDetail(login)
            .subscribeOn(mSchedulerProvider.io())
            .observeOn(mSchedulerProvider.ui())
            .subscribe({ detailUser ->
                mView.onUserDetailLoaded(detailUser)
                isLoading = false
                mView.hideLoadingView()
            }, {
                mView.hideLoadingView()
            })
    }
}