package com.exercise.githubuser.di.module

import android.content.Context
import com.exercise.githubuser.di.scope.PerActivity
import com.exercise.githubuser.domain.repository.UserDetailRepositoryImpl
import com.exercise.githubuser.domain.repository.UserRepository
import com.exercise.githubuser.domain.repository.UserRepositoryImpl
import com.exercise.githubuser.presentation.contract.UserDetailContract
import com.exercise.githubuser.presentation.presenter.UserDetailPresenter
import com.exercise.githubuser.presentation.ui.fragment.UserDetailFragment
import com.exercise.githubuser.utils.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class UserDetailModule {

    @PerActivity
    @Provides
    internal fun provideUserDetailView(view: UserDetailFragment)
            : UserDetailContract.View = view

    @PerActivity
    @Provides
    internal fun provideUserDetailRepository(context: Context)
            : UserRepository =
        UserRepositoryImpl(context)

    @PerActivity
    @Provides
    internal fun provideUserDetailPresenter(
        view: UserDetailContract.View,
        repository: UserDetailRepositoryImpl,
        provider: SchedulerProvider
    )
            : UserDetailContract.Presenter =
        UserDetailPresenter(view, repository, provider)
}