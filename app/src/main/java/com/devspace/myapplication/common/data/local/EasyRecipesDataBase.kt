package com.devspace.myapplication.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class EasyRecipesDataBase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}