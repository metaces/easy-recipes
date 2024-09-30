package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.main.data.RecipeListRepository
import com.devspace.myapplication.main.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.main.data.remote.MainService
import com.devspace.myapplication.main.data.remote.RecipeListRemoteDataSource

class EasyRecipesApplication: Application() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EasyRecipesDataBase::class.java,
            "easy_recipes_db"
        ).build()
    }

    private val mainService by lazy {
        RetrofitClient.retrofitInstance.create(MainService::class.java)
    }

    private val localDataSource: RecipeListLocalDataSource by lazy {
        RecipeListLocalDataSource(db.recipeDao())
    }

    private val remoteDataSource: RecipeListRemoteDataSource by lazy {
        RecipeListRemoteDataSource(mainService)
    }

    val recipeListRepository: RecipeListRepository by lazy {
        RecipeListRepository(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }
}