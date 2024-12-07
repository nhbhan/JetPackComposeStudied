package com.hannhb.myapplication

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hannhb.myapplication.ui.theme.BlueGray
import com.hannhb.myapplication.ui.theme.LightBlueWhite
import com.hannhb.myapplication.ui.theme.Roboto
import com.hannhb.myapplication.ui.theme.focusTextFieldText
import com.hannhb.myapplication.ui.theme.textFieldContainer
import com.hannhb.myapplication.ui.theme.unfocusContainer
import com.hannhb.myapplication.ui.theme.unfocusTextFieldText

@Composable
fun LoginScreen() {
    val emailState = remember {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()
    Surface() {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)) {
            val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
            Topsection(uiColor)
            Spacer(modifier = Modifier.height(24.dp))
            InputTextField(
                label = stringResource(id = R.string.email),
                value = emailState.value,
                onTextChanged = { emailState.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions.Default,
                modifier = Modifier,
                singleLine = true,
                trailingText = null,
                onTrailingClicked = { },
                uiColor = uiColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            InputTextField(
                label = stringResource(id = R.string.password),
                value = passwordState.value,
                onTextChanged = { passwordState.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions.Default,
                modifier = Modifier,
                singleLine = true,
                trailingText = stringResource(id = R.string.forgot_pass_word),
                onTrailingClicked = { },
                uiColor = uiColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = if (isSystemInDarkTheme()) Color(0xFF334155) else Color(
                        0xFF000113
                    )
                )
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = stringResource(id = R.string.or_continue_with),
                style = MaterialTheme.typography.labelMedium,
                color = if (isSystemInDarkTheme()) Color(0xFF94A3B8) else Color(0xFF64748B),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.padding(start = 24.dp, end = 24.dp)) {
                SocialLogin(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.google),
                    onClicked = { /*TODO*/ },
                    icon = R.drawable.google
                )

                SocialLogin(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.facebook),
                    onClicked = { /*TODO*/ },
                    icon = R.drawable.facebook
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(bottom = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(text =  buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF94A3B8),
                            fontSize = 14.sp,
                            fontFamily = Roboto,
                            fontWeight = FontWeight.Medium
                        )) {
                            append(stringResource(id = R.string.dont_have_acount))
                        }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Roboto,
                            color = uiColor
                        ) ){
                            append(stringResource(id = R.string.create_now))
                        }
                },
                    Modifier.clickable { /* TODO */ })

            }

        }

    }
}

@Composable
fun SocialLogin(
    modifier: Modifier,
    text: String,
    onClicked: () -> Unit,
    @DrawableRes icon: Int,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .height(40.dp)
            .padding(start = 8.dp, end = 8.dp)
            .socialBackground()
            .clickable { onClicked.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = "")
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSystemInDarkTheme()) Color(0xFF94A3B8) else Color(0xFF475569)
        )
    }

}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.socialBackground(): Modifier = composed(
    inspectorInfo = {},
    factory = {
        if (isSystemInDarkTheme()) {
            background(Color.Transparent).border(
                width = 1.dp,
                color = BlueGray,
                shape = RoundedCornerShape(4.dp)
            )
        } else {
            background(LightBlueWhite)
        }
    }
)

@Composable
fun InputTextField(
    label: String,
    value: String,
    onTextChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier,
    singleLine: Boolean,
    trailingText: String?,
    onTrailingClicked: () -> Unit,
    uiColor: Color,
    hint: String = "",
) {
    TextField(
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = uiColor,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        value = value,
        onValueChange = { onTextChanged.invoke(it) },
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.labelMedium,
                color = uiColor
            )
        },
        trailingIcon = {
            if (trailingText?.isNotEmpty() == true) {
                TextButton(onClick = { onTrailingClicked.invoke() }) {
                    Text(
                        text = stringResource(id = R.string.forgot_pass_word),
                        style = MaterialTheme.typography.titleMedium,
                        color = uiColor
                    )
                }

            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.unfocusContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer

        ),
        maxLines = 1
    )

}


@Composable
private fun Topsection(uiColor: Color) {
    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.46f),
            painter = painterResource(id = R.drawable.shape),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier.padding(top = 80.dp),
            verticalAlignment = Alignment.CenterVertically
//                    horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(42.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Text(
                    text = stringResource(id = R.string.the_tolet),
                    style = MaterialTheme.typography.headlineMedium,
                    color = uiColor
                )

                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = stringResource(id = R.string.find_your_house),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.headlineLarge,
            color = uiColor
        )
    }
}