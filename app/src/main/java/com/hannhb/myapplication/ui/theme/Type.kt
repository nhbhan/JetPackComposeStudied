package com.hannhb.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hannhb.myapplication.R

val Inter = FontFamily(
    listOf(Font(resId = R.font.inter_medium, weight = FontWeight.Medium))
)

val Rubik = FontFamily(
    listOf(Font(resId = R.font.rubik_bold, weight = FontWeight.Bold))
)

val Roboto = FontFamily(
    listOf(
        Font(resId = R.font.roboto_bold, weight = FontWeight.Bold),
        Font(R.font.roboto_regular, weight = FontWeight.Normal),
        Font(R.font.roboto_medium, weight = FontWeight.Medium)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Roboto,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold
    ),
    headlineMedium = TextStyle(
        fontFamily = Rubik,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),)
