package com.appruve

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.appruve.cameraapp.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

class BaseApplication : Application(), LifecycleObserver, HasActivityInjector,
    HasSupportFragmentInjector, HasServiceInjector {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

      //  DaggerAppComponent.builder().application(this).build().inject(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> {
        TODO("Not yet implemented")
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        TODO("Not yet implemented")
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        TODO("Not yet implemented")
    }

}