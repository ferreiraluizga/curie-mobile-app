package com.curie.curie.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.R
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme
import com.curie.curie.ui.theme.Typography
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WelcomeScreen(
    onContinueClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueNavy),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.curie_logo_nobackground),
                contentDescription = "Bem-vindo",
                modifier = Modifier
                    .size(200.dp)
            )

            Text(
                text = "sua identidade, seu futuro",
                style = Typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(100.dp))

            Button(
                onClick = onContinueClick,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = BlueNavy
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Já tenho conta", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.padding(25.dp))

            OutlinedButton(
                onClick = onContinueClick,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.5.dp, Color.White), // contorno visível
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Quero me cadastrar", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    CurieTheme {
        WelcomeScreen()
    }
}
