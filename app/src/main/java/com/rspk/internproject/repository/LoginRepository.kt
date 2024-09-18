package com.rspk.internproject.repository

import com.rspk.internproject.model.ForgotPassword
import com.rspk.internproject.model.LoginModel
import com.rspk.internproject.model.Otp
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.model.ResetPassword
import com.rspk.internproject.retrofit.SowLabApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

interface LoginRepository {
    suspend fun loginCheck(userName:String,password:String): Response<RegisterResponse>
    suspend fun forgotPassword(phoneNumber:String): Response<RegisterResponse>
    suspend fun sendOtp(otp:String): Response<RegisterResponse>
    suspend fun resetPassword(token:String,password:String,cPassword:String): Response<RegisterResponse>
}
class LoginRepositoryClass @Inject constructor(
    private val sowLabApi: SowLabApi,
): LoginRepository {
    override suspend fun loginCheck(userName: String, password: String) =
        withContext(context = Dispatchers.IO) {
            runBlocking {
                sowLabApi.loginUser(
                    LoginModel(
                        email = userName,
                        password = password,
                        type = "email",
                        role = "Farmer",
                        socialId = generateSocialId(userName,password,"email"),
                        deviceToken = generateSocialId(userName,password,"email")
                    )
                )
            }
        }

    override suspend fun forgotPassword(phoneNumber: String): Response<RegisterResponse> =
        withContext(context = Dispatchers.IO){
            runBlocking {
                sowLabApi.forgotPassword(
                    ForgotPassword(
                        mobile = phoneNumber
                    )
                )
            }
        }

    override suspend fun sendOtp(otp: String): Response<RegisterResponse> =
        withContext(Dispatchers.IO){
            runBlocking {
                sowLabApi.verifyOtp(
                    Otp(
                        otp = otp
                    )
                )
            }
        }

    override suspend fun resetPassword(
        token: String,
        password: String,
        cPassword: String
    ): Response<RegisterResponse> =
        withContext(Dispatchers.IO) {
            runBlocking {
                sowLabApi.resetPassword(
                    ResetPassword(
                        token = token,
                        password = password,
                        cPassword = cPassword
                    )
                )
            }
        }

}