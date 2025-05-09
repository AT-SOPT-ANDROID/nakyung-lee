package org.sopt.at.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import org.sopt.at.R
import org.sopt.at.domain.model.User
import org.sopt.at.domain.repository.UserRepository
import org.sopt.at.ui.signin.SignInViewModel
import org.sopt.at.ui.my.MyScreen
import org.sopt.at.ui.my.MyViewModel
import org.sopt.at.ui.screen.HistoryScreen
import org.sopt.at.ui.screen.HomeScreen
import org.sopt.at.ui.screen.LiveScreen
import org.sopt.at.ui.screen.SearchScreen
import org.sopt.at.ui.screen.ShortsScreen
import org.sopt.at.ui.signin.SignInScreen
import org.sopt.at.ui.signup.IdEntryScreen
import org.sopt.at.ui.signup.NicknameEntryScreen
import org.sopt.at.ui.signup.PasswordEntryScreen
import org.sopt.at.ui.signup.SignUpViewModel

@Composable
fun TvingApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val userRepository = remember { UserRepository(context = context) }
    val signInViewModel = remember { SignInViewModel(userRepository) }
    val signUpViewModel = remember { SignUpViewModel() }
    val myViewModel = remember { MyViewModel(userRepository) }

    val isLoggedIn = remember{MutableStateFlow(userRepository.isLoggedIn())}

    Scaffold(
        bottomBar = {
            if (isLoggedIn.value) {
                TvingBottomNavigation(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn.value) "home" else "signin",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("signin") {
                SignInScreen(
                    viewModel = signInViewModel,
                    navigateToSignUp = { navController.navigate("signup/id") },
                    navigateToHome = { navController.navigate("home") }
                )
            }

            composable("signup/id") {
                IdEntryScreen(
                    viewModel = signUpViewModel,
                    onBackClicked = { navController.popBackStack() },
                    onNextClicked = { navController.navigate("signup/nickname") }
                )
            }

            composable("signup/nickname") {
                NicknameEntryScreen(
                    viewModel = signUpViewModel,
                    onBackClicked = { navController.popBackStack() },
                    onNextClicked = { navController.navigate("signup/password") }
                )
            }

            composable("signup/password") {
                PasswordEntryScreen(
                    viewModel = signUpViewModel,
                    onBackClicked = { navController.popBackStack() },
                    onCompleteClicked = {
                            userRepository.registerUser(
                                User(
                                    signUpViewModel.uiState.value.loginId,
                                    signUpViewModel.uiState.value.password
                                )
                            )
                            navController.navigate("signin") {
                                popUpTo("signin") { inclusive = true }
                            }

                    },
                    signInSuccess = {isLoggedIn.value =true}
                )
            }

            composable("home") {
                HomeScreen(
                    onProfileClicked = {
                        navController.navigate("my")
                    }
                )
            }

            composable("shorts") {
                ShortsScreen()
            }

            composable("live") {
                LiveScreen()
            }

            composable("search") {
                SearchScreen()
            }

            composable("history") {
                HistoryScreen()
            }

            composable("my") {
                MyScreen(
                    viewModel = myViewModel,
                    onBackClick = { navController.popBackStack() },
                    onLogoutClick = { navController.navigate("signin")
                    userRepository.isLoggedIn()
                    }
                )
            }
        }
    }
}

@Composable
fun TvingBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem("home", "HOME", R.drawable.ic_home),
        BottomNavItem("shorts", "Shorts", R.drawable.ic_shorts),
        BottomNavItem("live", "LIVE", R.drawable.ic_live),
        BottomNavItem("search", "SEARCH", R.drawable.ic_search),
        BottomNavItem("history", "HISTORY", R.drawable.ic_history)
    )

    val showBottomNav = currentRoute in listOf("home", "shorts", "live", "search", "history")

    if (showBottomNav) {
        NavigationBar(
            containerColor = Color.Black,
            contentColor = Color.White
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.title,
                            tint = if (selected) Color.White else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (selected) Color.White else Color.Gray
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo("home") {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}


data class BottomNavItem(
    val route: String,
    val title: String,
    val iconResId: Int
)
