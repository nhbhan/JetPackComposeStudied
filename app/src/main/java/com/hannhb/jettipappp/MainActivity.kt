package com.hannhb.jettipappp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hannhb.jettipappp.components.CustomInputField
import com.hannhb.jettipappp.ui.theme.JetTipApppTheme
import com.hannhb.jettipappp.widgets.RoundedIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTipApppTheme {
                MyApp {
//                    TopHeader()
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Scaffold {
        Surface(
            modifier = Modifier.padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                content()
            }
        }
    }

}

@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.toDouble()) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        val totalFormatted = "%.2f".format(totalPerPerson)
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Per Person",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = "$${totalFormatted}",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp
                ),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
fun MainContent() {
    val range = IntRange(start = 1, endInclusive = 100)
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    val splitByState = remember {
        mutableStateOf(0)
    }
    BillForm(modifier = Modifier.padding(8.dp),
        splitByState = splitByState,
        totalPerPersonState = totalPerPersonState,
        tipAmountState = tipAmountState,
        range = range)
}

@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {},
) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val sliderPosition = remember {
        mutableStateOf(0f)
    }

    val range = IntRange(start = 1, endInclusive = 10)
    val tipPercentage = (sliderPosition.value * 100).toInt()

    TopHeader(totalPerPersonState.value)
    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CustomInputField(valueState = totalBillState,
                labelId = "Enter Bill",
                enable = true,
                singleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                    totalPerPersonState.value = calculatePerson(
                        tipPercentage = tipPercentage,
                        totalBill = totalBillState.value.toDouble(),
                        splitBy = splitByState.value
                    )
                }
            )
            if (validState) {
            Row(
                modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Split", modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    )
                )

                Spacer(modifier = modifier.width(120.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    RoundedIconButton(
                        imageVector = Icons.Default.Remove,
                        onClickEvent = {
                            splitByState.value =
                                if (splitByState.value > 1) splitByState.value - 1 else 1
                            totalPerPersonState.value = calculatePerson(
                                tipPercentage = tipPercentage,
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitByState.value
                            )
                        })

                    Text(
                        text = "${splitByState.value}",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp)
                    )

                    RoundedIconButton(
                        imageVector = Icons.Default.Add,
                        onClickEvent = {
                            if (splitByState.value < range.last) splitByState.value++
                            totalPerPersonState.value = calculatePerson(
                                tipPercentage = tipPercentage,
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitByState.value
                            )
                        })
                }

            }

            Row(
                modifier = Modifier.padding(
                    horizontal = 3.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = "Tip",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(200.dp))
                Text(
                    text = "$${tipAmountState.value}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${tipPercentage}%")
                Spacer(modifier = Modifier.height(14.dp))
                Slider(
                    value = sliderPosition.value,
                    onValueChange = { newValue ->
                        sliderPosition.value = newValue
                        tipAmountState.value = caculateTotalTip(
                            totalBill = totalBillState.value.toDouble(),
                            tipPercentage = tipPercentage
                        )
                        totalPerPersonState.value = calculatePerson(
                            tipPercentage = tipPercentage,
                            totalBill = totalBillState.value.toDouble(),
                            splitBy = splitByState.value
                        )

                    },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5,
                    onValueChangeFinished = {}
                )

            }
            } else {
                Box {

                }
            }
        }

    }
}

fun caculateTotalTip(totalBill: Double, tipPercentage: Int): Double {
    return if (totalBill > 1 && tipPercentage.toString().isNotEmpty())
        (totalBill * tipPercentage) / 100 else 0.0

}

fun calculatePerson(tipPercentage: Int, totalBill: Double, splitBy: Int): Double {
    val bill = caculateTotalTip(totalBill, tipPercentage) + totalBill
    return bill / splitBy
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipApppTheme {
        MyApp {
            Text(text = "Hello Again")
        }
    }
}