package com.hannhb.myapplication.screen.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.hannhb.myapplication.R
import com.hannhb.myapplication.data.DataOrException
import com.hannhb.myapplication.model.Weather
import com.hannhb.myapplication.model.WeatherItem
import com.hannhb.myapplication.navigation.WeatherScreen
import com.hannhb.myapplication.screen.setting.SettingViewModel
import com.hannhb.myapplication.utils.formatDate
import com.hannhb.myapplication.utils.formatDateTime
import com.hannhb.myapplication.utils.formatDecimals
import com.hannhb.myapplication.wigets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    settingViewModel: SettingViewModel,
    city: String?
) {
    val currentCity = if (city.isNullOrEmpty()) city else "Seatle"
     val unitFromDB = settingViewModel.settings.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDB.isNullOrEmpty()) {
        unit = unitFromDB.get(0).unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(isLoading = true)) {
            value = mainViewModel.getWeather(currentCity.valueToDefault(), unit = unit)
        }.value
        if (weatherData.isLoading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data, navController = navController, isImperial = isImperial)
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city?.name.valueToDefault() + ", ${weather.city?.country.valueToDefault()}",
//            icon = Icons.Default.ArrowBack,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreen.SEARCH_SCREEN.name)
            },
            elevation = 5.dp,
            onNavButtonClicked = {
                Log.d("TAG", "MainScaffold: Button clicked")
            })
    }) {
        MainContent(weather = weather, isImperial = isImperial)
    }
}

@Composable
fun MainContent(weather: Weather, isImperial: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.list?.firstOrNull()?.weather?.firstOrNull()?.icon.valueToDefault("")}.png"
    println(imageUrl)
    Column(modifier = Modifier
        .padding(24.dp)
        .padding(top = 70.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            formatDate(weather.list?.firstOrNull()?.dt?: 0),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface (modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xffffc400)
        ){
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl)
                Text(
                    formatDecimals( weather.list?.firstOrNull()?.temp?.day ?: 0.toDouble()) + "º",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(weather.list?.firstOrNull()?.weather?.firstOrNull()?.main.valueToDefault(),
                    fontStyle = FontStyle.Italic)
            }
        }

        HumidityWindPressureRow(weather.list?.firstOrNull(), isImperial = isImperial)
        Divider()
        SunriseAndSunset(weather.list?.firstOrNull())
        Text(text = "This Week",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold)

        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFFEE1EF),
            shape = RoundedCornerShape(14.dp)
        ) {

            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = weather.list ?: emptyList()) { item: WeatherItem ->
                    WeatherDetailItemRow(weatherItem = item)
                }

            }

        }
    }
    
}

@Composable
fun WeatherDetailItemRow(weatherItem: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather?.firstOrNull()?.icon.valueToDefault()}.png"
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(formatDate(weatherItem.dt.valueToDefault())
                .split(",").firstOrNull().valueToDefault(),
                modifier = Modifier.padding(start = 8.dp))

            WeatherStateImage(imageUrl)
            Surface(modifier = Modifier.padding(4.dp),
                shape = CircleShape.copy(CornerSize(10.dp)),
                color = Color(0xFFFFC400)
            ) {
                Text(weatherItem.weather?.firstOrNull()?.description.valueToDefault(),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.labelSmall)

            }

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )) {
                    append(formatDecimals( weatherItem.temp?.max.valueOrDefault()) + "°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )
                ) {
                    append(formatDecimals( weatherItem.temp?.min.valueOrDefault()) + "°")
                }
            })

        }
    }
}

@Composable
fun SunriseAndSunset(weather: WeatherItem?) {
    Row(modifier = Modifier
        .padding(top = 15.dp, bottom = 6.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Row (modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(25.dp))

            Text(text = formatDateTime(weather?.sunrise.valueToDefault()),
                style = MaterialTheme.typography.labelSmall)

        }

        Row (modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically){
            Text(text = formatDateTime(weather?.sunset.valueToDefault()),
                style = MaterialTheme.typography.labelSmall)

            Image(painter = painterResource(R.drawable.sunset),
                contentDescription = "sunset icon",
                modifier = Modifier.size(25.dp))
        }

    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem?, isImperial: Boolean) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp))

            Text("${weather?.humidity} %",
                style = MaterialTheme.typography.labelSmall)
        }

        Row( verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))

            Text("${weather?.pressure} psi",
                style = MaterialTheme.typography.labelSmall)
        }

        Row( verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp))

            Text("${formatDecimals(weather?.speed.valueOrDefault())} + ${if (isImperial) "mph" else "m/s"}",
                style = MaterialTheme.typography.labelSmall)
        }
    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .build()),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp))
}

fun String?.valueToDefault(value: String = "") : String {
    return this ?: value
}

fun Int?.valueToDefault(value: Int = 0): Int {
    return this ?: value
}

fun Double?.valueOrDefault(value: Double = 0.toDouble()): Double {
    return this ?: value
}

