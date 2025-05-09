package org.sopt.at.data.network.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignInDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SignInDataDto? = null
)

@Serializable
data class SignInDataDto(
    @SerialName("userId")
    val userId: Long
)