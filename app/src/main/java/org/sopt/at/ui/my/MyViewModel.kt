package org.sopt.at.ui.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.data.network.AuthService
import org.sopt.at.data.network.ServicePool
import org.sopt.at.domain.repository.UserRepository

data class MyUiState(
    val userId: String = "",
    val hasSubscription: Boolean = false,
    val subscriptionName: String = "",
    val cashAmount: Int = 0,
    val downloadCount: Int = 0

)

class MyViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val authService: AuthService = ServicePool.userService

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    private val _logoutEvent = MutableStateFlow(false)
    val logoutEvent: StateFlow<Boolean> = _logoutEvent.asStateFlow()

    fun getMyNickName(){
        val localUserID = userRepository.getLocalUserId()
        viewModelScope.launch {
            val response = authService.getMyNickname(localUserID)
            if (response.isSuccessful){
                _uiState.update { it.copy(userId = response.body()!!.data!!.nickname) }
            }
        }
    }

    fun logout() {
        userRepository.clearLocalUserId()
    }

}
