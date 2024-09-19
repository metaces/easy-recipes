package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.presentation.RecipesDetailViewModel
import com.devspace.myapplication.detail.presentation.ui.RecipesDetailScreen
import com.devspace.myapplication.main.presentation.MainViewModel
import com.devspace.myapplication.main.presentation.ui.MainScreen
import com.devspace.myapplication.main.presentation.ui.SplashScreen
import com.devspace.myapplication.search.presentation.ui.SearchRecipesScreen

@Composable
fun EasyRecipesApp(
    mainViewModel: MainViewModel,
    detailViewModel: RecipesDetailViewModel
) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splashScreen") {
        composable(route = "splashScreen") {
            SplashScreen(navController = navController)
        }
        composable(route = "mainScreen") {
            MainScreen(navController = navController, mainViewModel)
        }
        composable(route = "detailScreen/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("id"))
            RecipesDetailScreen(
                id = id,
                navController,
                detailViewModel
            )
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