package com.hannhb.myapplication.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hannhb.myapplication.model.QuestionItem
import com.hannhb.myapplication.screen.QuestionViewModel
import com.hannhb.myapplication.util.AppColor

@Composable
fun Questions(viewModel: QuestionViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.loading == true) {
//        CircularProgressIndicator()
    } else {
        val question = try {
            questions?.get(questionIndex.value)
        } catch (ex: Exception) {
            null
        }
      if (questions != null ) {
          QuestionDisplay(question = question !!,questionIndex = questionIndex, viewModel = viewModel) {
              questionIndex.value = questionIndex.value + 1
              
          }
      }
    }


}

fun Row(modifier: Modifier) {

}

@Composable
fun QuestionDisplay(question: QuestionItem,
                    questionIndex: MutableState<Int>,
                    viewModel: QuestionViewModel,
                    onNextClick:(Int)-> Unit) {
    val choicesState = remember(question) {
        question.choices?.toMutableList()
    }

    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question){
        {
            answerState.value = it
            correctAnswerState.value = choicesState?.get(it) == question.answer
    }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = AppColor.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker(questionIndex.value, viewModel.getTotalQuestionCount())
            DrawDottedLine(pathEffect = pathEffect)

            Column {
                if (questionIndex.value >=3) {
                    showProgress(score = questionIndex.value)
                }
                Text(
                    text = question.question.toString(),
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp,
                        color = AppColor.mOffWhite
                    )
                )
                //choices
                choicesState?.forEachIndexed { index, answerText ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp, brush = Brush.linearGradient(
                                colors = listOf(AppColor.mOffDarkPurple, AppColor.mOffDarkPurple)
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomEndPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically) {

                        RadioButton(selected = answerState.value == index,
                            onClick = { updateAnswer(index) },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = androidx.compose.material3.RadioButtonDefaults.colors(selectedColor = if (correctAnswerState.value == true && index  == answerState.value)
                                Color.Green.copy(alpha = 0.2f)
                            else
                                Color.Red.copy(0.2f)
                            ))
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                color = if (correctAnswerState.value == true
                                    && index == answerState.value){
                                    Color.Green

                                }else if (correctAnswerState.value == false && index  == answerState.value){
                                    Color.Red
                                } else {
                                    AppColor.mOffWhite
                                }, fontSize = 17.sp)){
                                append(answerText)

                            }
                        }
                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))

                        }
                }

                Button(onClick = { onNextClick(questionIndex.value) },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColor.mLightBlue,
                        contentColor = AppColor.mOffWhite
                    )
                ) {
                    Text(text = "Next", modifier = Modifier.padding(4.dp), color = AppColor.mOffWhite, fontSize = 17.sp)
                }
            }

        }

    }

}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = AppColor.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}

@Preview
@Composable
fun QuestionTracker(counter: Int = 10, outOff: Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColor.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(
                    style = SpanStyle(
                        color = AppColor.mLightGray,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                ) {
                    append("$outOff")
                }
            }
        }
    })
}

@Preview
@Composable
fun showProgress(score: Int = 12) {
    val progressFactor = remember(score) {
        mutableStateOf(score*0.005F)
    }
    val gradient = Brush.linearGradient(colors = listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    AppColor.mLightPurple,
                    AppColor.mLightPurple
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Button(contentPadding = PaddingValues(1.dp),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(text = (score*10).toString(),
                modifier = Modifier.clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColor.mOffWhite,
                textAlign = TextAlign.Center)
        }

    }

}