package com.devspace.myapplication.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.ApiService
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.main.data.MainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val mainService: MainService
): ViewModel() {
    private val _recipes = MutableStateFlow<List<RecipeDto>>(emptyList())
    val recipes: StateFlow<List<RecipeDto>> = _recipes

    init {
        fetchRandomRecipes()
    }

    private fun fetchRandomRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            if (recipes.value.isEmpty()) {
                val response =  mainService.getRandomRecipes()
                if (response.isSuccessful) {
                    val recipes = response.body()?.recipes ?: emptyList()
                    if (recipes.isNotEmpty()) {
                        _recipes.value = recipes
                    }
                } else {
                    Log.d("MainViewModel fetchRandomRecipes", "Error: ${response.errorBody()}")
                }
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
                val mainService = RetrofitClient.retrofitInstance.create(MainService::class.java)
                return MainViewModel(
                    mainService
                ) as T
            }
        }
    }
}