package com.example.androidmidterm.data.di

import com.example.androidmidterm.data.repository.gemini.GeminiRepository
import com.example.androidmidterm.data.repository.gemini.GeminiRepositoryImpl
import com.example.androidmidterm.data.repository.login.LoginRepository
import com.example.androidmidterm.data.repository.login.LoginRepositoryImpl
import com.example.androidmidterm.data.repository.profile.ProfileRepository
import com.example.androidmidterm.data.repository.profile.ProfileRepositoryImpl
import com.example.androidmidterm.data.repository.register.RegisterRepository
import com.example.androidmidterm.data.repository.register.RegisterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        impl: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindGeminiRepository(
        impl: GeminiRepositoryImpl
    ): GeminiRepository
}