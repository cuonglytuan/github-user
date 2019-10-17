package com.exercise.githubuser.di.module

import android.app.Application
import android.content.Context
import com.exercise.githubuser.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @PerApplication
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}