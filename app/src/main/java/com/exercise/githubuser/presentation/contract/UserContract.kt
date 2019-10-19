package com.exercise.githubuser.presentation.contract

import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.presentation.presenter.BasePresenter
import com.exercise.githubuser.presentation.ui.view.BaseView

interface UserContract {

    interface View : BaseView<Presenter> {

        fun initView()

        fun showLoadingView()
        fun hideLoadingView()

        fun onUserLoaded(user: List<User>)
        fun onUserEmpty()
        fun onUserFail()
    }

    interface Presenter : BasePresenter {
        fun getUser(startIndex: Long)
        fun getUserNext()
    }
}