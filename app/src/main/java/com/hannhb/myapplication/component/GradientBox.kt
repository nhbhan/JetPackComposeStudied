package com.hannhb.myapplication.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF0D9C42)
val tail600 = Color(0xFF59C1BD)

@Composable
fun GradientBox(
    modifier: Modifier =  Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.background(brush = Brush.linearGradient(
            listOf(Blue, tail600)
        ))
    ){
        content()
    }

}