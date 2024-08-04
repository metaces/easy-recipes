package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
        composable(route = "detailScreen/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("id"))
            RecipesDetailScreen(id = id, navController)
        }
        composable(
            route = "searchScreen/{query}",
            arguments = listOf(navArgument("query"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val query = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(query = query, navController)
            }

    }
}