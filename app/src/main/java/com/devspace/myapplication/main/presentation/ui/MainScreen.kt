package com.devspace.myapplication.main.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.data.remote.model.RecipeDto
import com.devspace.myapplication.components.ERHtmlText
import com.devspace.myapplication.components.ERSearchBar
import com.devspace.myapplication.main.presentation.MainViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val recipes by viewModel.recipes.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MainContent(
            recipes = recipes,
            onSearchClicked = { query ->
                val tempCleanQuery = query.trim()
                if (tempCleanQuery.isNotEmpty()){
                    navController.navigate(route = "searchScreen/$tempCleanQuery")
                }
            },
            onClick = {
                navController.navigate("detailScreen/${it.id}")
            }
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    recipes: RecipeListUiState,
    onSearchClicked: (String) -> Unit,
    onClick: (RecipeUiData) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        var query by remember {
            mutableStateOf("")
        }
        SearchSession(
            label = "Search",
            query = query,
            onQueryChange = {
                query = it
            },
            onSearchClicked = onSearchClicked
        )
        RecipesSession(
            label = "Recipes",
            recipesUiState = recipes,
            onClick = onClick
        )
    }
}

@Composable
fun SearchSession(
    label: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        text = label
    )
    ERSearchBar(
        query = query,
        placeHolder = "Search recipes",
        onValueChange = onQueryChange,
        onSearchClicked = {
            onSearchClicked.invoke(query)
        }
    )
}

@Composable
fun RecipesSession(
    label: String,
    recipesUiState: RecipeListUiState,
    onClick: (RecipeUiData) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        text = label
    )
    if (recipesUiState.isLoading) {
        Text(text = "Loading...")
    } else if (recipesUiState.isError) {
        Text(text = "Error: ${recipesUiState.errorMessage}")
    } else {
        RecipeList(recipes = recipesUiState.list, onClick = onClick)
    }
}

@Composable
private fun RecipeList(
    recipes: List<RecipeUiData>,
    onClick: (RecipeUiData) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(recipes) {
            RecipeItem(recipe = it, onClick = onClick)
        }
    }
}

@Composable
fun RecipeItem(
    recipe: RecipeUiData,
    onClick: (RecipeUiData) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick.invoke(recipe)
            }
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            model = recipe.image,
            contentDescription = "${recipe.title} Image"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            text = recipe.title)
        ERHtmlText(text = recipe.summary, maxLine = 3)
    }
}