package com.hannhb.movieapplication.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hannhb.movieapplication.model.Movie
import com.hannhb.movieapplication.model.getMovies
import com.hannhb.movieapplication.navigation.MovieScreen
import com.hannhb.movieapplication.widget.MovieRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigationController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
    ) { paddingValue ->
        MyMainContent(paddingValues = paddingValue, navigationController = navigationController)
    }
}

@Composable
fun MyMainContent(
    paddingValues: PaddingValues,
    navigationController: NavController,
    listMovies: MutableList<Movie> = getMovies()
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(12.dp)
    ) {
        LazyColumn {
            items(items = listMovies) {
                MovieRow(movie = it){movie ->
                    navigationController.navigate(route = MovieScreen.DETAIL_SCREEN.name + "/${movie}")
                }
            }
        }

    }
}


