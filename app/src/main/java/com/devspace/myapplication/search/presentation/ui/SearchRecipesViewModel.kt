package com.devspace.myapplication.search.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.model.SearchRecipesDto
import com.devspace.myapplication.search.data.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchRecipesViewModel(
    private val searchService: SearchService
): ViewModel() {

    private val _recipes = MutableStateFlow<List<SearchRecipesDto>>(emptyList())
    val recipes: StateFlow<List<SearchRecipesDto>> = _recipes

    fun fetchRecipesByQuery(query: String) {
        if (_recipes.value.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = searchService.searchRecipes(query)
                if (response.isSuccessful) {
                    _recipes.value = response.body()?.results ?: emptyList()
                } else {
                    Log.d("SearchRecipesViewModel fetchRecipesByQuery", "Error: ${response.errorBody()}")
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
                val searchService = RetrofitClient.retrofitInstance.create(SearchService::class.java)
                return SearchRecipesViewModel(
                    searchService
                ) as T
            }
        }
    }
}