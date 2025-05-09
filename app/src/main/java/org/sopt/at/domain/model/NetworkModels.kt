package org.sopt.at.domain.model

data class SignupRequest(val id: String, val password: String, val nickname: String)
data class SignupResponse(val isSuccess: Boolean, val message: String)
data class LoginRequest(val id: String, val password: String)
data class LoginResponse(val userId: Int)
data class NicknameResponse(val nickname: String)
