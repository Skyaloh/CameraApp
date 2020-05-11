package ke.co.kazi.di

import com.appruve.cameraapp.api.Webservice
import com.appruve.cameraapp.utils.BASE_URL
import com.appruve.cameraapp.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton


@Module
class WebserviceModule {

    @Singleton
    @Provides
    fun providesWebservice(retrofit: Retrofit): Webservice {
        return retrofit.create(Webservice::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor { chain ->
                    val request = chain.request().newBuilder().build()
                    chain.proceed(request)
                }
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor
                .Logger { message -> Timber.d(message) })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}