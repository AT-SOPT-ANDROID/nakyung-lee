package org.sopt.at.ui.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.sopt.at.ui.components.TvingButton
import org.sopt.at.ui.components.TvingTextField

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navigateToSignUp: () -> Unit,
    navigateToMyView: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.isSignInSuccessful) {
        if (uiState.isSignInSuccessful) {
            viewModel.resetSignInState()
            navigateToMyView(uiState.userId)
        }
    }

    LaunchedEffect(uiState.signInError) {
        uiState.signInError?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(message = error)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    text = "TVING ID 로그인",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                TvingTextField(
                    value = uiState.userId,
                    onValueChange = viewModel::updateUserId,
                    label = "아이디",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TvingTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    label = "비밀번호",
                    isPassword = true,
                    isPasswordVisible = uiState.isPasswordVisible,
                    onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.signIn()
                        }
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                TvingButton(
                    text = "로그인하기",
                    onClick = viewModel::signIn,
                    enabled = uiState.userId.isNotEmpty() && uiState.password.isNotEmpty()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "아이디 찾기",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {  }
                    )

                    Text(
                        text = " | ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "비밀번호 찾기",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {  }
                    )

                    Text(
                        text = " | ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "회원가입",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { navigateToSignUp() }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(thickness = 1.dp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "이 사이트는 Google reCAPTCHA로 보호되며,\nGoogle 개인정보 처리방침과 서비스 약관이 적용됩니다.",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
