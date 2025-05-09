package org.sopt.at.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserLocalDataSource(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var userId: Long
        get() = sharedPreferences.getLong(KEY_USER_ID, DEFAULT_USER_ID)
        set(value) {
            sharedPreferences.edit() { putLong(KEY_USER_ID, value) }
        }

    fun clear() {
        sharedPreferences.edit() { clear() }
    }

    companion object {
        private const val PREFERENCES_NAME = "user_data"
        private const val KEY_USER_ID = "user_id"
        private const val DEFAULT_USER_ID = -1L
    }
}
