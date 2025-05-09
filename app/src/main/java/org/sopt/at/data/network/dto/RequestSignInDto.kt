package org.sopt.at.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignInDto(
    @SerialName("loginId")
    val loginId: String,
    @SerialName("password")
    val password: String,
)