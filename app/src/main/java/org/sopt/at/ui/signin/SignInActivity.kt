package org.sopt.at.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import org.sopt.at.model.User
import org.sopt.at.repository.UserRepository
import org.sopt.at.ui.my.MyActivity
import org.sopt.at.ui.signup.SignUpActivity
import org.sopt.at.ui.theme.TvingTheme


class SignInActivity : ComponentActivity() {
    private lateinit var viewModel: SignInViewModel
    private lateinit var userRepository: UserRepository

    private val signUpLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val userId = result.data?.getStringExtra("USER_ID") ?: ""
                val password = result.data?.getStringExtra("USER_PASSWORD") ?: ""

                userRepository.registerUser(User(userId, password))

                Toast.makeText(
                    this,
                    "회원가입이 완료되었습니다. 로그인해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this,
                    "회원가입은 완료되었으나 정보를 저장하는 중 오류가 발생했습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userRepository = UserRepository()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(userRepository)
        )[SignInViewModel::class.java]

        setContent {
            TvingTheme {
                SignInScreen(
                    viewModel = viewModel,
                    navigateToSignUp = {
                        val intent = Intent(this, SignUpActivity::class.java)
                        signUpLauncher.launch(intent)
                    },
                    navigateToMyView = { userId ->
                        val intent = Intent(this, MyActivity::class.java).apply {
                            putExtra("USER_ID", userId)
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }

    class ViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SignInViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
