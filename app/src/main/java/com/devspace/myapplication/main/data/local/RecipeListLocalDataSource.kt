package com.devspace.myapplication.main.data.local

import com.devspace.myapplication.common.data.local.RecipeDao
import com.devspace.myapplication.common.data.local.RecipeEntity
import com.devspace.myapplication.common.data.model.Recipe

class RecipeListLocalDataSource(
    private val recipeDao: RecipeDao
) {

    suspend fun getRecipes(): List<Recipe> {
        val recipes = recipeDao.getRecipes()
        return recipes.map { recipeEntity ->
            Recipe(
                id = recipeEntity.id,
                title = recipeEntity.title,
                image = recipeEntity.image,
                summary = recipeEntity.summary
            )
        }
    }

    suspend fun updateRecipes(recipes: List<Recipe>) {
        val recipeEntities = recipes.map { recipe ->
            RecipeEntity(
                id = recipe.id,
                title = recipe.title,
                image = recipe.image,
                summary = recipe.summary
            )
        }
        recipeDao.insertAll(recipeEntities)

    }

}