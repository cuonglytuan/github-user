package com.exercise.githubuser.di.module

import android.content.Context
import com.exercise.githubuser.di.scope.PerActivity
import com.exercise.githubuser.domain.repository.UserRepository
import com.exercise.githubuser.domain.repository.UserRepositoryImpl
import com.exercise.githubuser.presentation.contract.UserContract
import com.exercise.githubuser.presentation.presenter.UserPresenter
import com.exercise.githubuser.presentation.ui.fragment.UserFragment
import com.exercise.githubuser.utils.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @PerActivity
    @Provides
    internal fun provideUserView(view: UserFragment)
            : UserContract.View = view

    @PerActivity
    @Provides
    internal fun provideUserRepository(context: Context)
            : UserRepository =
        UserRepositoryImpl(context)

    @PerActivity
    @Provides
    internal fun provideUserPresenter(
        view: UserContract.View,
        repository: UserRepositoryImpl,
        provider: SchedulerProvider
    )
            : UserContract.Presenter =
        UserPresenter(view, repository, provider)
}