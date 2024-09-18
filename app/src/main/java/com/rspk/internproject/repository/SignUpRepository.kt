package com.rspk.internproject.repository

import com.rspk.internproject.model.FarmerEatsModel
import com.rspk.internproject.model.RegisterResponse
import com.rspk.internproject.model.TextFieldOutput
import com.rspk.internproject.retrofit.SowLabApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.security.MessageDigest
import javax.inject.Inject

interface SignUpRepository {
    suspend fun postData(): Response<RegisterResponse>
}

class SignUpRepositoryClass @Inject constructor(
    private val sowLabApi: SowLabApi,
    private val textFieldOutput: TextFieldOutput
) : SignUpRepository {

    override suspend fun postData() =
        withContext(context = Dispatchers.IO) {
                runBlocking {
                    sowLabApi.registerUser(
                        FarmerEatsModel(
                            fullName = textFieldOutput.fullName,
                            email = textFieldOutput.email,
                            phone = textFieldOutput.phone,
                            password = textFieldOutput.password,
                            role = textFieldOutput.role ,
                            businessName = textFieldOutput.businessName,
                            informalName = textFieldOutput.informalName,
                            address = textFieldOutput.address,
                            city = textFieldOutput.city,
                            state = textFieldOutput.state,
                            zipCode = textFieldOutput.zipCode,
                            registrationProof = textFieldOutput.registrationProof,
                            businessHours = textFieldOutput.businessHours,
                            socialId = if(textFieldOutput.socialId != "") textFieldOutput.socialId else generateSocialId(textFieldOutput.email,textFieldOutput.password,textFieldOutput.type),
                            type = textFieldOutput.type,
                            deviceToken = if(textFieldOutput.socialId != "") textFieldOutput.socialId else generateSocialId(textFieldOutput.email,textFieldOutput.password,textFieldOutput.type))
                        )
                }
            }

}

fun generateSocialId(email: String, password: String, type: String): String {
    val input = "$email|$password|$type"
    val hash = MessageDigest.getInstance("SHA-256")
        .digest(input.toByteArray())
        .joinToString("") { "%02x".format(it) }

    return hash
}
