package com.rspk.internproject.di

import com.rspk.internproject.retrofit.SowLabApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiInterfaceDi {

    @Provides
    fun provideApiInterface(): SowLabApi{
        return SowLabApi.getApiInstance()
    }
}