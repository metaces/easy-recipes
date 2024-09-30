package com.devspace.myapplication.detail.data

import com.devspace.myapplication.common.data.remote.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun getRecipeById(@Path("id") id: String): Response<RecipeDto>

}