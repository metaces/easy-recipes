package com.devspace.myapplication.main.data

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import com.devspace.myapplication.main.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.main.data.remote.MainService
import com.devspace.myapplication.main.data.remote.RecipeListRemoteDataSource

class RecipeListRepository(
    private val localDataSource: RecipeListLocalDataSource,
    private val remoteDataSource: RecipeListRemoteDataSource
) {
    suspend fun getRecipes(): Result<List<Recipe>?> {
        return try {
            val remoteResult = remoteDataSource.getRandomRecipes()
            if (remoteResult.isSuccess) {
                val recipes = remoteResult.getOrNull() ?: emptyList()
                if (recipes.isNotEmpty()) {
                    localDataSource.updateRecipes(recipes)
                }
                Result.success(recipes)
            } else {
                val localResult = localDataSource.getRecipes()
                if (localResult.isNotEmpty()) {
                    Result.success(localResult)
                } else {
                    Result.failure(remoteResult.exceptionOrNull() ?: Exception("Unknown error"))
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }

    }

}