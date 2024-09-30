package com.devspace.myapplication.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.EasyRecipesApplication
import com.devspace.myapplication.main.data.RecipeListRepository
import com.devspace.myapplication.main.presentation.ui.RecipeListUiState
import com.devspace.myapplication.main.presentation.ui.RecipeUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RecipeListRepository
): ViewModel() {
    private val _recipes = MutableStateFlow<RecipeListUiState>(RecipeListUiState())
    val recipes: StateFlow<RecipeListUiState> = _recipes

    init {
        fetchRandomRecipes()
    }

    private fun fetchRandomRecipes() {
        _recipes.value = RecipeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result =  repository.getRecipes()
            if (result.isSuccess) {
                val recipes = result.getOrNull()
                if (recipes != null) {
                    val recipeUiData = recipes.map {
                        RecipeUiData(
                            id = it.id,
                            title = it.title,
                            image = it.image,
                            summary = it.summary
                        )
                    }
                    _recipes.value = RecipeListUiState(list = recipeUiData)
                }
            } else {
                _recipes.value = RecipeListUiState(isError = true, errorMessage = result.exceptionOrNull()?.message)
                Log.d("MainViewModel fetchRandomRecipes", "Error: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel(
                    repository = (application as EasyRecipesApplication).recipeListRepository
                ) as T
            }
        }
    }
}