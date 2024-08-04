package com.devspace.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random?number=20")
    fun getRandomRecipes(): Call<RecipesResponse>

    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeById(@Path("id") id: String): Call<RecipeDto>

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}