package com.devspace.myapplication.main.presentation.ui

data class RecipeListUiState(
    val list: List<RecipeUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something wen wrong"
)

data class RecipeUiData(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)
