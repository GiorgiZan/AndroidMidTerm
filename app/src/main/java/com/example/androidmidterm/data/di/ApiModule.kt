package com.example.androidmidterm.data.di

import android.content.Context
import com.example.androidmidterm.BuildConfig
import com.example.androidmidterm.common.ApiHelper
import com.example.androidmidterm.data.remote.retrofit.RetrofitGeminiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }
// AIzaSyBQ8P4ZcbULmqM5HxjELrswH6Calp35IX8

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideGeminiRetrofit(client: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/v1beta/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiHelper(@ApplicationContext context: Context): ApiHelper {
        return ApiHelper(context)
    }


    @Provides
    @Singleton
    fun provideGeminiRetrofitService(retrofit: Retrofit): RetrofitGeminiService {
        return retrofit.create(RetrofitGeminiService::class.java)
    }


}