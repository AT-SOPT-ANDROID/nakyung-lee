package org.sopt.at.repository

import kotlinx.coroutines.flow.MutableStateFlow
import org.sopt.at.model.User


class UserRepository {
    private val _registeredUsers = MutableStateFlow<List<User>>(emptyList())

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
}
