package org.sopt.at.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUpDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SignUpData? = null
)

@Serializable
data class SignUpData(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String
)
