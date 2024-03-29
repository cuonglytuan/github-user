package com.exercise.githubuser.utils.scheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ImmediateSchedulerProvider : BaseSchedulerProvider {
    override fun computation(): Scheduler = Schedulers.trampoline()
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler = Schedulers.trampoline()
}