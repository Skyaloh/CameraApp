package ke.co.kazi.di

import com.appruve.cameraapp.ui.upload.UploadImageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeUploadImageFragment(): UploadImageFragment
}