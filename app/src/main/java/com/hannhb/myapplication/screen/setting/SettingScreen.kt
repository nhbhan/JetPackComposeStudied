package com.hannhb.myapplication.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hannhb.myapplication.model.Unit
import com.hannhb.myapplication.wigets.WeatherAppBar
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController,
                  settingViewModel: SettingViewModel = hiltViewModel()
) {
    var unitToggleChange by remember { mutableStateOf(false) }
    val measurementUnit = listOf("Imperial (F), Metric (C)")
    val coroutineScope = rememberCoroutineScope()
    val choiceFromDB = settingViewModel.settings.collectAsState().value
    val defaultChoice = if (choiceFromDB.isNullOrEmpty()) measurementUnit[0] else choiceFromDB[0].unit
    var choiceState by remember { mutableStateOf(defaultChoice) }

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Setting",
                isMainScreen = false,
                navController = navController,
                icon = Icons.Default.ArrowBack,
                onNavButtonClicked = {
                    navController.popBackStack()
                })
        }
    ) { paddingValues ->

        Column (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "Change Unit of Measurement",
                modifier = Modifier.padding(bottom = 15.dp))

            IconToggleButton(onCheckedChange = {
                unitToggleChange = !it
                choiceState = if (unitToggleChange) "Imperial (F)" else "Metric (C)"
            }, checked = unitToggleChange,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .clip(RectangleShape)
                    .background(Color.Magenta.copy(alpha = 0.4f))
                    .padding(5.dp)) {

                Text(text = if (unitToggleChange) "Fahrenheit ºF" else "Celsius ºC")

            }

            Button(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(43.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFBE42)),
                onClick = {
                    coroutineScope.launch {
                        settingViewModel.deleteAllUnit()
                        settingViewModel.insertUnit(Unit(unit = choiceState))
                    }
                }) {
                Text(text = "Save",
                    style = TextStyle(color = Color.White),
                    fontSize = 17.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}