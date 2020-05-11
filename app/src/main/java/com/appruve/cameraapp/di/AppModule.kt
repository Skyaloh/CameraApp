package com.appruve.cameraapp.di

import android.app.Application
import com.appruve.cameraapp.AppExecutors
import com.appruve.cameraapp.api.Webservice
import com.appruve.cameraapp.repository.ServiceRepository

import dagger.Module
import dagger.Provides

import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    fun providesServiceRepository(webservice: Webservice, appExecutors: AppExecutors): ServiceRepository = ServiceRepository( webservice, appExecutors)


}