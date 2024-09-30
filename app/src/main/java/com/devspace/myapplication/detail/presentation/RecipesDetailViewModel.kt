package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesDetailViewModel(
    private val detailService: DetailService
): ViewModel() {

    private val _recipe = MutableStateFlow<RecipeDto?>(null)
    var recipe: StateFlow<RecipeDto?> = _recipe

    fun fetchRecipeById(id: String) {
        if (_recipe.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = detailService.getRecipeById(id)
                if (response.isSuccessful) {
                    _recipe.value = response.body()
                } else {
                    Log.d("RecipesDetailViewModel fetchRecipeById", "Error: ${response.errorBody()}")
                }
            }
        }
    }

    fun cleanRecipe() {
        viewModelScope.launch {
            delay(1000L)
            _recipe.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val detailService = RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return RecipesDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}