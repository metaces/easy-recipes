package com.devspace.myapplication.main.data.remote

import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import com.devspace.myapplication.common.data.remote.model.SearchRecipesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    @GET("recipes/random?number=20")
    suspend fun getRandomRecipes(): Response<RecipesResponse>
}