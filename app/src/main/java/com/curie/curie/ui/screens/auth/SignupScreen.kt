package com.curie.curie.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.R
import com.curie.curie.ui.components.auth.DateOfBirthPicker
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme
import com.curie.curie.ui.theme.Shapes
import com.curie.curie.ui.theme.Typography
import com.curie.curie.ui.theme.montserratFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignupScreen(
    onContinueClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Bem-vindo",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    label = { Text("Nome Completo") },
                    value = email,
                    onValueChange = { newText -> email = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .shadow(
                            elevation = 4.dp,
                            shape = Shapes.medium,
                            clip = true
                        )
                        .background(
                            color = Color.White,
                            shape = Shapes.medium
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = BlueNavy
                    )
                )

                Spacer(Modifier.padding(8.dp))

                TextField(
                    label = { Text("Telefone") },
                    value = email,
                    onValueChange = { newText -> email = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .shadow(
                            elevation = 4.dp,
                            shape = Shapes.medium,
                            clip = true
                        )
                        .background(
                            color = Color.White,
                            shape = Shapes.medium
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = BlueNavy
                    )
                )

                Spacer(Modifier.padding(8.dp))

                TextField(
                    label = { Text("E-mail") },
                    value = email,
                    onValueChange = { newText -> email = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .shadow(
                            elevation = 4.dp,
                            shape = Shapes.medium,
                            clip = true
                        )
                        .background(
                            color = Color.White,
                            shape = Shapes.medium
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = BlueNavy
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                DateOfBirthPicker()

                Spacer(Modifier.padding(8.dp))

                TextField(
                    label = { Text("Senha") },
                    value = password,
                    onValueChange = { newText -> password = newText },
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Outlined.VisibilityOff
                        else
                            Icons.Outlined.Visibility

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                                tint = BlueNavy
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .shadow(
                            elevation = 4.dp,
                            shape = Shapes.medium,
                            clip = true
                        )
                        .background(
                            color = Color.White,
                            shape = Shapes.medium
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = BlueNavy
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                
                Spacer(Modifier.padding(8.dp))

                TextField(
                    label = { Text("Confirmar Senha") },
                    value = password,
                    onValueChange = { newText -> password = newText },
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Outlined.VisibilityOff
                        else
                            Icons.Outlined.Visibility

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                                tint = BlueNavy
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .shadow(
                            elevation = 4.dp,
                            shape = Shapes.medium,
                            clip = true
                        )
                        .background(
                            color = Color.White,
                            shape = Shapes.medium
                        ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = BlueNavy
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onContinueClick,
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueNavy,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Entrar", fontSize = 18.sp)
                }

                Spacer(Modifier.weight(1f))

                Text(
                    buildAnnotatedString {
                        append("Já é cadastrado?\n")
                        withStyle(style = SpanStyle(
                            color = BlueNavy,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = montserratFontFamily
                        )
                        ) {
                            append("Faça Login")
                        }
                    },
                    modifier = Modifier
                        .clickable { onRegisterClick() }
                        .padding(vertical = 16.dp),
                    color = Color.DarkGray,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    CurieTheme {
        SignupScreen()
    }
}