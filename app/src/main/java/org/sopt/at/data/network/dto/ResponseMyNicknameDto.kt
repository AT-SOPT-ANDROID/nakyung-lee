package org.sopt.at.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyNicknameDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: MyInfoData? = null
)

@Serializable
data class MyInfoData(
    @SerialName("nickname")
    val nickname: String
)
