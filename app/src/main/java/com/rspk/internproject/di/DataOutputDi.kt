package com.rspk.internproject.di

import com.rspk.internproject.model.TextFieldOutput
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataOutputDi {

    @Provides
    @Singleton
    fun provideDataOutput(): TextFieldOutput {
        return TextFieldOutput()
    }

}