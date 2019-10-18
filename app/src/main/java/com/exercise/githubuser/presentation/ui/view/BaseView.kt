package com.exercise.githubuser.presentation.ui.view

import com.exercise.githubuser.presentation.presenter.BasePresenter

interface BaseView<in T : BasePresenter> {
    fun setPresenter(presenter: T)
}