package com.turlaypi231.androidcarsharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.turlaypi231.androidcarsharing.ui.theme.AndroidCarSharingTheme
import com.turlaypi231.androidcarsharing.view.IntroScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turlaypi231.androidcarsharing.view.RegisterScreen

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

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            IntroScreen(
                onLoginClick = { email, password ->
                    println("Спроба входу: $email")
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { name, email, phone, pass ->
                    println("Реєстрація користувача: $name")
                    navController.navigate("login")
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}