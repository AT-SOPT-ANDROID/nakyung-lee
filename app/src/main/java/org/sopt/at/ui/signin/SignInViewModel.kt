package org.sopt.at.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.at.repository.UserRepository

class SignInViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()


    fun updateUserId(userId: String) {
        _uiState.value = _uiState.value.copy(userId = userId)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }

    fun signIn() {
        viewModelScope.launch {
            val userId = _uiState.value.userId
            val password = _uiState.value.password

            println("로그인 시도 - ID: $userId, Password: $password")

            val isAuthenticated = userRepository.authenticateUser(userId, password)

            _uiState.value = _uiState.value.copy(
                isSignInSuccessful = isAuthenticated,
                signInError = if (!isAuthenticated) "아이디 또는 비밀번호가 일치하지 않습니다." else null
            )
        }
    }

    fun resetSignInState() {
        _uiState.value = _uiState.value.copy(
            isSignInSuccessful = false,
            signInError = null
        )
    }

    data class SignInUiState(
        val userId: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isSignInSuccessful: Boolean = false,
        val signInError: String? = null
    )
}
