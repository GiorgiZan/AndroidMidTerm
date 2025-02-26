package com.example.androidmidterm.data.di

import com.example.androidmidterm.data.repository.login.LoginRepository
import com.example.androidmidterm.data.repository.login.LoginRepositoryImpl
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
}