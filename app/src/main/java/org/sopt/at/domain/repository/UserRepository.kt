package org.sopt.at.domain.repository

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import org.sopt.at.data.local.UserLocalDataSource
import org.sopt.at.domain.model.User


class UserRepository(context: Context) {
    private val _registeredUsers = MutableStateFlow<List<User>>(emptyList())
    private val userLocalDataSource = UserLocalDataSource(context =context)

    fun isLoggedIn():Boolean{
        return (userLocalDataSource.userId != DEFAULT_USER_ID)
    }

    fun getLocalUserId():Long{
        return userLocalDataSource.userId
    }

    fun setLocalUserId(userId:Long){
        userLocalDataSource.userId = userId
    }

    fun clearLocalUserId(){
        userLocalDataSource.userId = DEFAULT_USER_ID
    }

    fun registerUser(user: User) {
        val currentUsers = _registeredUsers.value.toMutableList()

        val existingUserIndex = currentUsers.indexOfFirst { it.userId == user.userId }

        if (existingUserIndex >= 0) {
            currentUsers[existingUserIndex] = user
        } else {
            currentUsers.add(user)
        }

        _registeredUsers.value = currentUsers

        println("registered user: ${_registeredUsers.value}")
    }

    fun authenticateUser(userId: String, password: String): Boolean {
        val result = _registeredUsers.value.any {
            it.userId == userId && it.password == password
        }

        println("로그인 시도 - ID: $userId, Password: $password, 결과: $result")
        println("현재 등록된 사용자: ${_registeredUsers.value}")

        return result
    }

    companion object {
        private const val DEFAULT_USER_ID = -1L
    }
}
