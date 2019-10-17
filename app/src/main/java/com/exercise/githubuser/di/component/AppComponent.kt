package com.exercise.githubuser.di.component

import android.app.Application
import com.exercise.githubuser.application.GithubUserApplication
import com.exercise.githubuser.di.module.ApplicationModule
import com.exercise.githubuser.di.module.BindingModule
import com.exercise.githubuser.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [(BindingModule::class), (ApplicationModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: GithubUserApplication)
}