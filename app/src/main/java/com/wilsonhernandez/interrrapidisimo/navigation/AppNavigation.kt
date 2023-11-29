package com.wilsonhernandez.interrrapidisimo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wilsonhernandez.interrrapidisimo.Greeting
import com.wilsonhernandez.interrrapidisimo.ui.view.HomeScreen
import com.wilsonhernandez.interrrapidisimo.ui.view.SplashScreen
import com.wilsonhernandez.interrrapidisimo.ui.viewmodel.HomeViewModel

@Composable
fun AppNavigation(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.MainScreen.route) {
            HomeScreen(viewModel)
        }
    }
}