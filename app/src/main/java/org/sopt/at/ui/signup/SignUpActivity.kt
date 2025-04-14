package org.sopt.at.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import org.sopt.at.ui.theme.TvingTheme

class SignUpActivity : ComponentActivity() {
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        setContent {
            TvingTheme {
                val currentPage by viewModel.currentPage.collectAsState()
                val uiState by viewModel.uiState.collectAsState()

                if (uiState.isRegistrationSuccessful) {
                    try {
                        val resultIntent = Intent()
                        resultIntent.putExtra("USER_ID", uiState.userId)
                        resultIntent.putExtra("USER_PASSWORD", uiState.password)
                        setResult(RESULT_OK, resultIntent)

                        viewModel.resetRegistrationState()
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "오류가 발생했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                when (currentPage) {
                    SignUpViewModel.SignUpPage.ID -> {
                        IdEntryScreen(
                            userId = uiState.userId,
                            onUserIdChange = viewModel::updateUserId,
                            onNextClick = {
                                if (viewModel.validateUserId()) {
                                    viewModel._currentPage.value = SignUpViewModel.SignUpPage.PASSWORD
                                } else {
                                    Toast.makeText(
                                        this,
                                        "아이디는 영문 대/소문자 또는 영문 소문자, 숫자 조합 6~12자리여야 합니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            onBackClick = { finish() },
                            error = uiState.idError
                        )
                    }
                    SignUpViewModel.SignUpPage.PASSWORD -> {
                        PasswordEntryScreen(
                            password = uiState.password,
                            onPasswordChange = viewModel::updatePassword,
                            isPasswordVisible = uiState.isPasswordVisible,
                            onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
                            onNextClick = {
                                if (viewModel.validatePassword()) {
                                    viewModel._uiState.value = viewModel.uiState.value.copy(
                                        isRegistrationSuccessful = true
                                    )
                                } else {
                                    Toast.makeText(
                                        this,
                                        "비밀번호는 영문, 숫자, 특수문자(~!@#$%^&*) 조합 8~15자리여야 합니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            onBackClick = {
                                viewModel._currentPage.value = SignUpViewModel.SignUpPage.ID
                            },
                            error = uiState.passwordError
                        )
                    }
                }
            }
        }
    }
}
