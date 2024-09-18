package com.rspk.internproject.di

import com.rspk.internproject.repository.LoginRepository
import com.rspk.internproject.repository.LoginRepositoryClass
import com.rspk.internproject.repository.SignUpRepository
import com.rspk.internproject.repository.SignUpRepositoryClass
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryDi {

    @Binds
    abstract fun provideSignUpRepository(
        signUpRepositoryClass: SignUpRepositoryClass
    ): SignUpRepository

    @Binds
    abstract fun provideLoginRepository(
        loginRepositoryClass: LoginRepositoryClass
    ): LoginRepository

}