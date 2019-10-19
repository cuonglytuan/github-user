package com.exercise.githubuser.di.module

import com.exercise.githubuser.di.scope.PerActivity
import com.exercise.githubuser.presentation.ui.fragment.UserDetailFragment
import com.exercise.githubuser.presentation.ui.fragment.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [(UserModule::class)])
    abstract fun bindingUserFragment(): UserFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [(UserDetailModule::class)])
    abstract fun bindingUserDetailFragment(): UserDetailFragment
}