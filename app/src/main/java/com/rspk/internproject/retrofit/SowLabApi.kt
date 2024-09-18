package com.rspk.internproject.retrofit

import com.rspk.internproject.model.FarmerEatsModel
import com.rspk.internproject.model.ForgotPassword
import com.rspk.internproject.model.LoginModel
import com.rspk.internproject.model.Otp
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.model.ResetPassword
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface SowLabApi {

    @POST("user/register")
    suspend fun registerUser(
        @Body registerRequest: FarmerEatsModel
    ): Response<RegisterResponse>

    @POST("user/login")
    suspend fun loginUser(
        @Body registerLogin: LoginModel
    ): Response<RegisterResponse>

    @POST("user/forgot-password")
    suspend fun forgotPassword(
        @Body forgotPassword: ForgotPassword
    ): Response<RegisterResponse>

    @POST("user/verify-otp")
    suspend fun verifyOtp(
        @Body otp: Otp
    ): Response<RegisterResponse>

    @POST("user/reset-password")
    suspend fun resetPassword(
        @Body resetPassword: ResetPassword
    ): Response<RegisterResponse>

    companion object{
        private const val BASE_URL = "https://sowlab.com/assignment/"

        private val client = OkHttpClient.Builder().build()

        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun getApiInstance(): SowLabApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SowLabApi::class.java)
        }

    }
}