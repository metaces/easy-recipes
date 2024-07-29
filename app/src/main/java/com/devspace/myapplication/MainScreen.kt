package com.devspace.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    MainContent()
}

@Composable
fun MainContent() {
    Text(text = "Main Content")
}