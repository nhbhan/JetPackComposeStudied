package com.hannhb.myapplication.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.hannhb.myapplication.component.Questions

@Composable
fun TriviaHome(viewModel: QuestionViewModel= hiltViewModel()) {
    Questions(viewModel)

}