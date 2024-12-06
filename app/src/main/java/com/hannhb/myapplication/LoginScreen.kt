package com.hannhb.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hannhb.myapplication.component.GradientBox
import com.hannhb.myapplication.component.MyTextField

@Composable
fun LoginScreen() {
    val imeVisible by rememberImeState()
    val userNameState = remember {
        mutableStateOf("")
    }
    val passwordSate = remember {
        mutableStateOf("")
    }
    val localFocusManager = LocalFocusManager.current
    GradientBox(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val animatedUpperSection by animateFloatAsState(
                targetValue = if (imeVisible) 0f else 0.35f,
                label = ""
            )

            AnimatedVisibility(visible = !imeVisible, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(animatedUpperSection),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Welcome to login",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White)
                    .pointerInput(Unit) {
                                        detectTapGestures ( onTap = {
                                            localFocusManager.clearFocus()
                                        } )
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSmallScreenHeight()) {
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                } else {
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                if (isSmallScreenHeight()) {
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                } else {
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }
                MyTextField(
                    label = "User Name/ Phone Number",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {}
                    ),
                    onValueChanged = { userNameState.value = it},
                    value = userNameState.value
                )
//                Spacer(modifier = Modifier.height(16.dp))
                MyTextField(
                    label = "Password",
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    ),
                    onValueChanged = {passwordSate.value = it},
                    value = passwordSate.value,
                    trailingIcon = Icons.Rounded.Lock,
                    maxLength = 8
                )

                if (imeVisible) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF0054D3)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Log in",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(500)
                            )
                        )
                    }

                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color(0xFF0054D3)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Log in",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(500)
                                )
                            )
                        }
                    }

                }
            }

        }

    }
}