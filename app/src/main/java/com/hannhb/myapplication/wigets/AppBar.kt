package com.hannhb.myapplication.wigets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hannhb.myapplication.model.Favourite
import com.hannhb.myapplication.navigation.WeatherScreen
import com.hannhb.myapplication.screen.favourite.FavouriteViewModel
import com.hannhb.myapplication.screen.main.valueToDefault
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onNavButtonClicked: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (showDialog.value) {
        showDropDowDialogMenu(showDialog, navController)
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }

                IconButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Vert")
                }
            } else Box {}

        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "NavIcon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.clickable {
                        onNavButtonClicked.invoke()
                    })
            }

            if (isMainScreen) {
                val isAlreadyFavourite =
                    favouriteViewModel.favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",").firstOrNull().valueToDefault())
                    }
                if (isAlreadyFavourite.isNullOrEmpty()) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                coroutineScope.launch {
                                    favouriteViewModel.insertFavourite(
                                        Favourite(
                                            city = dataList.firstOrNull().valueToDefault(),
                                            country = dataList.lastOrNull().valueToDefault()
                                        )
                                    ).run {
                                        showIt.value = true
                                    }
                                }

                            }, tint = Color.Red.copy(alpha = 0.6f))
                } else {
                    showIt.value = false
                    Box{}
                }
                ShowToast(context, showIt)
            }
        },
        modifier = Modifier
            .shadow(elevation = elevation)
            .background(Color.Transparent)
    )

}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "Added city to the Favourite List", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun showDropDowDialogMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember { mutableStateOf(true) }
    val items = mutableListOf("Favourites", "About", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(top = 45.dp, right = 20.dp)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        DropdownMenu(modifier = Modifier
            .width(140.dp)
            .background(color = Color.White),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {

            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    showDialog.value = false
                    expanded = false
                },
                    text = {
                        Row {
                            val icon = when (text) {
                                "About" -> Icons.Default.Info
                                "Settings" -> Icons.Default.Settings
                                "Favourites" -> Icons.Default.Favorite
                                else -> Icons.Default.Lock
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                            Text(text = text,
                                modifier = Modifier
                                    .clickable {
                                        clickDropdownItem(
                                            text,
                                            navController
                                        )
                                    }
                                    .padding(start = 4.dp, end = 4.dp),
                                fontWeight = FontWeight(300)
                            )
                        }

                    })
            }

        }
    }

}


fun clickDropdownItem(text: String, navController: NavController) {
    val screen = when (text) {
        "About" -> WeatherScreen.ABOUT_SCREEN.name
        "Favourites" -> WeatherScreen.FAVORITE_SCREEN.name
        "Settings" -> WeatherScreen.SETTING_SCREEN.name
        else -> ""
    }
    navController.navigate(screen)
}

