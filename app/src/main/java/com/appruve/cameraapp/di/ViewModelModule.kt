package com.appruve.cameraapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appruve.ViewModelKey
import com.appruve.cameraapp.ui.main.MainViewModel
import com.appruve.cameraapp.viewmodel.ServiceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(ServiceViewModel::class)
    abstract fun bindServiceViewModel(viewModel: ServiceViewModel): ViewModel


}