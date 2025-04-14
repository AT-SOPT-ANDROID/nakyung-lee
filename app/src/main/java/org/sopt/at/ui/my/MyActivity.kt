package org.sopt.at.ui.my

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import org.sopt.at.ui.signin.SignInActivity
import org.sopt.at.ui.theme.TvingTheme

class MyActivity : ComponentActivity() {
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val userId = intent.getStringExtra("USER_ID") ?: "사용자"
        viewModel.setUserInfo(userId)

        viewModel.updateSubscriptionInfo(false, 0)

        setContent {
            TvingTheme {
                MyScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() },
                    onLogoutClick = {
                        val intent = Intent(this, SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
