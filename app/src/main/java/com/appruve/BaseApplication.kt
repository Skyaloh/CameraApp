package com.appruve

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.appruve.cameraapp.BuildConfig
import com.appruve.cameraapp.di.DaggerAppComponent
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : Application(), LifecycleObserver, HasActivityInjector,
    HasSupportFragmentInjector, HasFragmentInjector, HasServiceInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent.builder().application(this).build().inject(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> = supportFragmentInjector

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = fragmentInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector


}