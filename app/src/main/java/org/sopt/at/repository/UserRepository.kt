package org.sopt.at.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.at.model.User


class UserRepository {
    private val _registeredUsers = MutableStateFlow<List<User>>(emptyList())
    val registeredUsers: StateFlow<List<User>> = _registeredUsers.asStateFlow()

    fun registerUser(user: User) {
        val currentUsers = _registeredUsers.value.toMutableList()

        val existingUserIndex = currentUsers.indexOfFirst { it.userId == user.userId }

        if (existingUserIndex >= 0) {
            currentUsers[existingUserIndex] = user
        } else {
            currentUsers.add(user)
        }

        _registeredUsers.value = currentUsers

        println("현재 등록된 사용자: ${_registeredUsers.value}")
    }

    fun authenticateUser(userId: String, password: String): Boolean {
        val result = _registeredUsers.value.any {
            it.userId == userId && it.password == password
        }

        println("인증 시도 - ID: $userId, Password: $password, 결과: $result")
        println("현재 등록된 사용자: ${_registeredUsers.value}")

        return result
    }

    fun addTestUser(userId: String, password: String) {
        registerUser(User(userId, password))
    }
}
