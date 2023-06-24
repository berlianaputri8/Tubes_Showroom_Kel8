package id.ac.unpas.showroom.di

import android.content.Context
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.ac.unpas.showroom.networks.MobilApi
import id.ac.unpas.showroom.networks.MotorApi
import id.ac.unpas.showroom.networks.PromoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context:
                            Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(
                "https://ppm-api.gusdya.net/api/"
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMobilApi(retrofit: Retrofit):
            MobilApi {
        return retrofit.create(MobilApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMotorApi(retrofit: Retrofit):
            MotorApi {
        return retrofit.create(MotorApi::class.java)
    }

    @Provides
    @Singleton
    fun providePromoApi(retrofit: Retrofit):
            PromoApi {
        return retrofit.create(PromoApi::class.java)
    }

}