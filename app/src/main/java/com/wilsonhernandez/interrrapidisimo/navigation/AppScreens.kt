package com.wilsonhernandez.interrrapidisimo.navigation

sealed class AppScreens (val route:String){
    object SplashScreen:AppScreens("spash_screen")
    object MainScreen:AppScreens("main_screen")
}
