package com.exercise.githubuser.presentation.contract

import com.exercise.githubuser.data.entity.UserDetail
import com.exercise.githubuser.presentation.presenter.BasePresenter
import com.exercise.githubuser.presentation.ui.view.BaseView

interface UserDetailContract {

    interface View : BaseView<Presenter> {

        fun initView()

        fun showLoadingView()
        fun hideLoadingView()

        fun onUserDetailLoaded(userDetail: UserDetail)
    }

    interface Presenter : BasePresenter {
        fun getUserDetail(login: String)
    }
}