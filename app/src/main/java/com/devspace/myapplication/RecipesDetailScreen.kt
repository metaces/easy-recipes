package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.components.ERHtmlText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipesDetailScreen(id: String, navController: NavHostController) {

    var recipeDto by remember { mutableStateOf<RecipeDto?>(null) }
    val service = RetrofitClient.retrofitInstance.create(ApiService::class.java)

    service.getRecipeById(id).enqueue(object : Callback<RecipeDto> {
        override fun onResponse(call: Call<RecipeDto>, response: Response<RecipeDto>) {
            if (response.isSuccessful) {
                recipeDto = response.body()
            } else {
                Log.d("RecipesDetailScreen", "Error: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<RecipeDto>, t: Throwable) {
            Log.d("RecipesDetailScreen", "Error: ${t.message}")
        }

    })

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        recipeDto?.let {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = it.title
                    )
                }
                RecipesDetailContent(recipe = it)
            }
        }
    }
}

@Composable
fun RecipesDetailContent(recipe: RecipeDto) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )
        ERHtmlText(
            text = recipe.summary
        )
    }
}