package org.sopt.at.ui.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {
    val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateUserId(userId: String) {
        _uiState.value = _uiState.value.copy(userId = userId)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun validateUserId(): Boolean {
        val userId = _uiState.value.userId
        val idPattern = "^[a-zA-Z0-9]{6,12}$".toRegex()

        val isValid = idPattern.matches(userId)
        if (!isValid) {
            _uiState.value = _uiState.value.copy(
                idError = "아이디는 영문 대/소문자 또는 영문 소문자, 숫자 조합 6~12자리여야 합니다."
            )
        } else {
            _uiState.value = _uiState.value.copy(idError = null)
        }

        return isValid
    }

    fun validatePassword(): Boolean {
        val password = _uiState.value.password
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#\$%^&*])[A-Za-z\\d~!@#\$%^&*]{8,15}$".toRegex()

        val isValid = passwordPattern.matches(password)
        if (!isValid) {
            _uiState.value = _uiState.value.copy(
                passwordError = "비밀번호는 영문, 숫자, 특수문자(~!@#$%^&*) 조합 8~15자리여야 합니다."
            )
        } else {
            _uiState.value = _uiState.value.copy(passwordError = null)
        }

        return isValid
    }

    fun resetRegistrationState() {
        _uiState.value = _uiState.value.copy(
            isRegistrationSuccessful = false
        )
    }

    data class SignUpUiState(
        val userId: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val idError: String? = null,
        val passwordError: String? = null,
        val isRegistrationSuccessful: Boolean = false
    )
}
