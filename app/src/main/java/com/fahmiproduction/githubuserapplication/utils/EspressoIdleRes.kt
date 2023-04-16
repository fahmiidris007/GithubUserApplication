package com.fahmiproduction.githubuserapplication.utils

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

object EspressoIdleRes {
    private val idleResCount = CountingIdlingResource("global_source")
    val idleRes: IdlingResource get() = idleResCount
    fun increment() = idleResCount.increment()
    fun decrement() = idleResCount.decrement()
}