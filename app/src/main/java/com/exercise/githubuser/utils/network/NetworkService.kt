package com.exercise.githubuser.utils.network

import com.exercise.githubuser.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {
    companion object {
        private fun getOkHttpClient(
            timeoutConnection: Long = 1500,
            timeoutSocket: Long = 1500
        ): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(timeoutConnection, TimeUnit.MILLISECONDS)
                .readTimeout(timeoutSocket, TimeUnit.MILLISECONDS)
                .build()
        }

        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.END_POINT)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

    }
}