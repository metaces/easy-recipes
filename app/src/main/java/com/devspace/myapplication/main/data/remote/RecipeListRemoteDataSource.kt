package com.devspace.myapplication.main.data.remote

import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import retrofit2.Response

class RecipeListRemoteDataSource(
    private val mainService: MainService
) {

    suspend fun getRandomRecipes(): Result<List<Recipe>?> {
         return try {
            val response =  mainService.getRandomRecipes()
            if (response.isSuccessful) {
               val recipes  = mappedRecipe(response)
                Result.success(recipes)
            } else {
                Result.failure(Exception(response.errorBody().toString()))
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private fun mappedRecipe(response: Response<RecipesResponse>): List<Recipe>? {
        val recipes = response.body()?.recipes?.map {
            Recipe(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary
            )
        }
        return recipes
    }
}