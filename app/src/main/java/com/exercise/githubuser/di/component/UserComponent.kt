package com.exercise.githubuser.di.component

import com.exercise.githubuser.presentation.ui.fragment.UserFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface UserComponent : AndroidInjector<UserFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<UserFragment>()

}