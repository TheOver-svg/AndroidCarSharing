package com.turlaypi231.androidcarsharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turlaypi231.androidcarsharing.ui.theme.AndroidCarSharingTheme
import com.turlaypi231.androidcarsharing.view.IntroScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turlaypi231.androidcarsharing.services.TokenManager
import com.turlaypi231.androidcarsharing.view.HistoryOfTripsScreen
import com.turlaypi231.androidcarsharing.view.MainScreen
import com.turlaypi231.androidcarsharing.view.ProfileScreen
import com.turlaypi231.androidcarsharing.view.RegisterScreen
import com.turlaypi231.androidcarsharing.viewModel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCarSharingTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            IntroScreen(
                onLoginClick = { email, password ->
                    authViewModel.login(email, password) {
                        navController.navigate("mainScreen") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { name, email, phone, pass ->
                    authViewModel.register(name, email, phone, pass) {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("trips_history") {
            HistoryOfTripsScreen()
        }

        composable("profile") {
            ProfileScreen(
                onHistoryClick = {
                    navController.navigate("trips_history")
                },
                onLogoutClick = {
                    TokenManager.token = null
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("mainScreen") {
            MainScreen(navController = navController)
        }
    }
}