package com.hannhb.myapplication.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hannhb.myapplication.navigation.WeatherScreen
import com.hannhb.myapplication.wigets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Search",
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack,
            onAddActionClicked = {
                navController.popBackStack()
            })
    }) { it ->
        Surface(modifier = Modifier.padding(it)) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                SearchBar(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)) {cityName->
                    navController.navigate(WeatherScreen.MAIN_SCREEN.name + "/${cityName}")
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    onSearch:(String) -> Unit
) {
    val searchState = rememberSaveable {
        mutableStateOf("")
    }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val isValidate = remember(searchState.value) {
        searchState.value.trim().isNotEmpty()
    }
    Column(modifier) {
        CommonTextField(
            value = searchState,
            placeHolder =" Please enter ...",
            keyBoardAction = KeyboardActions {
                if (!isValidate) return@KeyboardActions
                onSearch?.invoke(searchState.value)
                keyBoardController?.hide()
                searchState.value = ""
            }
        )
        val hashMap: Map<String, String> = mapOf()
        val hashMap1: HashMap<String, String> = hashMapOf()
       hashMap1.entries

    }
}

@Composable
fun CommonTextField(value: MutableState<String>,
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Search,
                    placeHolder: String,
                    keyBoardAction: KeyboardActions = KeyboardActions.Default) {
    OutlinedTextField(value = value.value,
        onValueChange = {value.value = it},
        label = { Text(text = placeHolder) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction),
        keyboardActions = keyBoardAction,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.LightGray,
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)


    )
}
