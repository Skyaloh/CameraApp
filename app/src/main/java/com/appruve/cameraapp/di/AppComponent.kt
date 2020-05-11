package ke.co.kazi.di

import android.app.Application
import com.appruve.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    WebserviceModule::class,
    AppModule::class,
    ActivityModule::class,
    ViewModelModule::class,
    FragmentModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseApplication: BaseApplication)

}