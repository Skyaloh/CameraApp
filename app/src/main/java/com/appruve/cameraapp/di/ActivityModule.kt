package ke.co.kazi.di

import com.appruve.cameraapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {


    @ContributesAndroidInjector
    internal abstract fun contributeDashboardActivity(): MainActivity

}