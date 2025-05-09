package org.sopt.at.data.network

import org.sopt.at.data.network.ApiConstraints.API
import org.sopt.at.data.network.ApiConstraints.AUTH
import org.sopt.at.data.network.ApiConstraints.USERS
import org.sopt.at.data.network.ApiConstraints.VERSION
import org.sopt.at.data.network.dto.RequestSignInDto
import org.sopt.at.data.network.dto.RequestSignUpDto
import org.sopt.at.data.network.dto.ResponseMyNicknameDto
import org.sopt.at.data.network.dto.ResponseSignInDto
import org.sopt.at.data.network.dto.ResponseSignUpDto
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.*

interface AuthService {
    @POST("$API/$VERSION/$AUTH/signup")
    suspend fun signUp(
        @Body request: RequestSignUpDto,
    ): Response<ResponseSignUpDto>

    @POST("$API/$VERSION/$AUTH/signin")
    suspend fun signIn (
        @Body request: RequestSignInDto,
    ): Response<ResponseSignInDto>

    @GET("$API/$VERSION/$USERS/me")
    suspend fun getMyNickname(
        @Header("userId") userId: Long
    ): Response<ResponseMyNicknameDto>
}
