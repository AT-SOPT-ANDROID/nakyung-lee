package org.sopt.at.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.sopt.at.data.network.AuthService
import org.sopt.at.data.network.ServicePool
import org.sopt.at.data.network.dto.RequestSignUpDto
import org.sopt.at.data.network.dto.ResponseSignUpDto

data class SignUpUiState(
    val loginId: String = "",
    val password: String = "",
    val nickname: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    private val authService: AuthService = ServicePool.userService


    fun updateLoginId(id: String) {
        _uiState.update { it.copy(loginId = id) }
    }

    fun updatePassword(pw: String) {
        _uiState.update { it.copy(password = pw) }
    }

    fun updateNickname(nick: String) {
        _uiState.update { it.copy(nickname = nick) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun setId(id: String) {
        _uiState.update { it.copy(loginId = id) }
    }

    fun setNickname(nick: String) {
        _uiState.update { it.copy(nickname = nick) }
    }

    fun singUpClicked(password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        _uiState.update { it.copy(password = password) }
        signUp(onSuccess = onSuccess, onFailure = onFailure)
    }

    private fun signUp(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val signUpRequest = RequestSignUpDto(
            loginId = _uiState.value.loginId,
            nickname = _uiState.value.nickname,
            password = _uiState.value.password
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val response = authService.signUp(signUpRequest)

                if (response.isSuccessful) {
                    Log.e("zz", "success" + response.message().toString())
                    _uiState.update { it.copy(errorMessage = response.body()?.message ?: "") }
                    onSuccess()
                } else{
                    val error = response.errorBody()?.string().toString()

                    val errorMessage = try {
                        val parsedError = Json.decodeFromString<ResponseSignUpDto>(error ?: "")
                        parsedError.message
                    } catch (e: Exception) {
                        "오류 메시지를 불러올 수 없습니다."
                    }

                    _uiState.update { it.copy(isLoading = false, errorMessage = errorMessage) }
                    onFailure(errorMessage)

                }
            } catch (e: Exception) {
                val error = e.message ?: "네트워크 오류"
                _uiState.update { it.copy(isLoading = false, errorMessage = error) }
                onFailure(error)
            }
        }
    }

}
