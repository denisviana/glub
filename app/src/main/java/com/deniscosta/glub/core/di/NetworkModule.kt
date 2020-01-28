package com.deniscosta.glub.core.di

import com.deniscosta.glub.core.api.ErrorClass
import com.deniscosta.glub.core.api.RestApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {

    single { createOkHttpClient() }
    single { provideRetrofit(get(), "https://raw.githubusercontent.com/PeixeUrbano/desafio-android/master/api/") }
    single { provideRestApiService(get()) }
    //factory { provideConverter( get() ) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun provideRestApiService(retrofit: Retrofit): RestApiService {
    return retrofit.create<RestApiService>(RestApiService::class.java)
}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String) : Retrofit{
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

