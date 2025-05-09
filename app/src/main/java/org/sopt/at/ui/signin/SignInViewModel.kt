package org.sopt.at.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.data.network.AuthService
import org.sopt.at.data.network.ServicePool
import org.sopt.at.data.network.dto.RequestSignInDto
import org.sopt.at.domain.repository.UserRepository

data class SignInUiState(
    val userId: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val showSnackBar:Boolean = false,
    val errorMessage: String = "",
    val loginState: Boolean = false,
)
class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val authService: AuthService = ServicePool.userService

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> get() = _uiState

    fun updateUserId(newId: String) {
        _uiState.update { it.copy(userId = newId) }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun changeSnackBarVisible(visible:Boolean){
        _uiState.update { it.copy(showSnackBar = visible) }
    }

    fun changePasswordVisible(visible:Boolean){
        _uiState.update { it.copy(passwordVisible = visible) }
    }

    fun changeLoginState(){
        _uiState.update { it.copy(loginState = !uiState.value.loginState) }
    }

    fun signIn() {
        val current = _uiState.value
        if (current.userId.isEmpty() || current.password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "아이디와 비밀번호를 입력해주세요.") }
            return
        }

        val request = RequestSignInDto(current.userId, current.password)

        viewModelScope.launch {
            val response = authService.signIn(request)
            if(response.isSuccessful){
                _uiState.update { it.copy(loginState = true) }
                userRepository.setLocalUserId(response.body()!!.data!!.userId)
            }else {
                _uiState.update {
                    it.copy(errorMessage = "로그인 실패: ${response.message()}",)
                }
                changeSnackBarVisible(visible = true)
            }
        }
    }
}