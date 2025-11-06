package com.curie.curie.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.R
import com.curie.curie.data.model.RegisterRequest
import com.curie.curie.ui.components.auth.DateOfBirthPicker
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme
import com.curie.curie.ui.theme.Shapes
import com.curie.curie.ui.theme.Typography
import com.curie.curie.ui.theme.montserratFontFamily

@Composable
fun SignupScreen(
    onContinueClick: (RegisterRequest) -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    // 🔹 Estados dos campos
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nascimento by remember { mutableStateOf("") }

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
            contentDescription = "Imagem de cadastro",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Campo Nome
                CurieTextField(label = "Nome Completo", value = name, onValueChange = { name = it })

                Spacer(Modifier.height(8.dp))

                // Campo Telefone
                CurieTextField(
                    label = "Telefone",
                    value = telefone,
                    onValueChange = { telefone = it },
                    keyboardType = KeyboardType.Phone
                )

                Spacer(Modifier.height(8.dp))

                // Campo E-mail
                CurieTextField(
                    label = "E-mail",
                    value = email,
                    onValueChange = { email = it },
                    keyboardType = KeyboardType.Email
                )

                Spacer(Modifier.height(8.dp))

                // Campo Descrição
                CurieTextField(
                    label = "Descrição (opcional)",
                    value = descricao,
                    onValueChange = { descricao = it }
                )

                Spacer(Modifier.height(8.dp))

                // Campo Data de nascimento
                DateOfBirthPicker(
                    onDateSelected = { selected ->
                        nascimento = selected // Exemplo: "2025-11-06"
                    }
                )

                Spacer(Modifier.height(8.dp))

                // Campo Senha
                PasswordField(
                    label = "Senha",
                    value = password,
                    onValueChange = { password = it },
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = it }
                )

                Spacer(Modifier.height(8.dp))

                // Campo Confirmar Senha
                PasswordField(
                    label = "Confirmar Senha",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    passwordVisible = passwordVisible,
                    onVisibilityChange = { passwordVisible = it }
                )

                Spacer(Modifier.height(24.dp))

                // Botão de cadastro
                Button(
                    onClick = {
                        val request = RegisterRequest(
                            name = name,
                            email = email,
                            password = password,
                            descricao = descricao,
                            nascimento = "${nascimento}T00:00:00",
                            telefone = telefone
                        )
                        onContinueClick(request)
                    },
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueNavy,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Cadastrar", fontSize = 18.sp)
                }

                Spacer(Modifier.weight(1f))

                Text(
                    buildAnnotatedString {
                        append("Já é cadastrado?\n")
                        withStyle(
                            style = SpanStyle(
                                color = BlueNavy,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = montserratFontFamily
                            )
                        ) {
                            append("Faça Login")
                        }
                    },
                    modifier = Modifier
                        .clickable { onLoginClick() }
                        .padding(vertical = 16.dp),
                    color = Color.DarkGray,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/* ---------- COMPONENTES REUTILIZÁVEIS ---------- */

@Composable
fun CurieTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = Shapes.medium, clip = true)
            .background(Color.White, shape = Shapes.medium),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = BlueNavy
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    TextField(
        label = { Text(label) },
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            val image = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
            IconButton(onClick = { onVisibilityChange(!passwordVisible) }) {
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                    tint = BlueNavy
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = Shapes.medium, clip = true)
            .background(Color.White, shape = Shapes.medium),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = BlueNavy
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

/* ---------- PREVIEW ---------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    CurieTheme {
        SignupScreen()
    }
}
