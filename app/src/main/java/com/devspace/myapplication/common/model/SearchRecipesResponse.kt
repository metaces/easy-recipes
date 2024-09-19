package com.devspace.myapplication.common.model

data class SearchRecipesResponse(
    val results: List<SearchRecipesDto>
)

data class SearchRecipesDto(
    val id: Int,
    val title: String,
    val image: String,
)
