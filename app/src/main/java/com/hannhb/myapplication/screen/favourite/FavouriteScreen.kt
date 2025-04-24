package com.hannhb.myapplication.screen.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hannhb.myapplication.model.Favourite
import com.hannhb.myapplication.navigation.WeatherScreen
import com.hannhb.myapplication.wigets.WeatherAppBar
import kotlinx.coroutines.launch

@Composable
fun FavouriteScreen(navController: NavController,
                    favouriteViewModel: FavouriteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                isMainScreen = false,
                onNavButtonClicked = {
                    navController.popBackStack()
                },
                title = "Favourites Screen",
                icon = Icons.Default.ArrowBack
            )
        }

    ) {paddingValues ->
        Surface(modifier = Modifier.padding(5.dp).padding(paddingValues).fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favouriteViewModel.favList.collectAsState().value
                LazyColumn {
                    items(list){
                        CityRow(it, navController, favouriteViewModel)
                    }
                }
            }
        }

    }
}

@Composable
fun CityRow(favourite: Favourite, navController: NavController, favouriteViewModel: FavouriteViewModel) {
    val coroutine = rememberCoroutineScope()
    Surface(modifier = Modifier.padding(3.dp)
        .fillMaxWidth()
        .height(50.dp)
        .clickable {
            navController.navigate(WeatherScreen.MAIN_SCREEN.name +  "/${favourite.city}")
        },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFB2DFDB)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(favourite.city, modifier = Modifier.padding(4.dp))
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape.copy(CornerSize(4.dp)),
                color = Color(0xFFD1E3E1)) {

                Text(favourite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.labelSmall)

            }
            Icon(Icons.Rounded.Delete,
                contentDescription = null,
                tint = Color.Red.copy(alpha = 0.3f),
                modifier = Modifier.clickable {
                    coroutine.launch {
                        favouriteViewModel.deleteFavourite(favourite)
                    }
                })
        }
    }

}
