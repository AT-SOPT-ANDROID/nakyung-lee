package org.sopt.at.ui.my

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * My 화면의 상태와 동작을 관리하는 ViewModel
 */
class MyViewModel : ViewModel() {

    // UI 상태 관리하는 StateFlow
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    // 로그아웃 상태 관리하는 StateFlow
    private val _logoutEvent = MutableStateFlow(false)
    val logoutEvent: StateFlow<Boolean> = _logoutEvent.asStateFlow()

    fun setUserInfo(userId: String) {
        _uiState.value = _uiState.value.copy(
            userId = userId
        )
    }

    fun updateSubscriptionInfo(hasSubscription: Boolean, cashAmount: Int) {
        _uiState.value = _uiState.value.copy(
            hasSubscription = hasSubscription,
            cashAmount = cashAmount
        )
    }

    fun logout() {
        _logoutEvent.value = true
    }

    fun resetLogoutEvent() {
        _logoutEvent.value = false
    }

    data class MyUiState(
        val userId: String = "",
        val hasSubscription: Boolean = false,
        val subscriptionName: String = "",
        val cashAmount: Int = 0,
        val downloadCount: Int = 0
    )
}
