package com.rbraithwaite.yourmovielog

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var loggingTree: Timber.Tree

    override fun onCreate() {
        super.onCreate()

        Timber.plant(loggingTree)
    }
}
