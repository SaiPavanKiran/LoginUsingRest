package com.rspk.internproject.di

import android.content.Context
import com.rspk.internproject.dataStore
import com.rspk.internproject.datastore.IsUserSignedIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreDi {

    @Provides
    @Singleton
    fun isUserSignedIn(
        @ApplicationContext context: Context
    ): IsUserSignedIn {
        return IsUserSignedIn(context.dataStore)
    }
}