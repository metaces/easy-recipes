package com.devspace.myapplication

import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.common.model.SearchRecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/recipes/complexSearch?")
    fun searchRecipes(@Query("query") query: String): Call<SearchRecipesResponse>
}