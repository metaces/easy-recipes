package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun EasyRecipesApp() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splashScreen") {
        composable(route = "splashScreen") {
            SplashScreen(navController = navController)
        }
        composable(route = "mainScreen") {
            MainScreen(navController = navController)
        }

    }
}