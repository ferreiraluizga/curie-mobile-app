package com.curie.curie.ui.components.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.curie.curie.R
import com.curie.curie.ui.theme.BlueDark
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.Typography

@Composable
fun PerfilImage(
    userId: Int,
    userName: String,
    userUsername: String,
    onEditClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight() // ocupa só o necessário
            .fillMaxWidth(),     // preenche horizontalmente
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabeçalho com gradiente e imagem
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BlueNavy, BlueDark)
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "avatar image",
                modifier = Modifier
                    .offset(y = 50.dp)
                    .clip(CircleShape)
                    .size(100.dp)
            )
        }

        Spacer(Modifier.height(60.dp))

        // Dados e botão centralizados
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userName,
                style = Typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "$userUsername",
                style = Typography.labelLarge,
                color = Color.Gray
            )
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onEditClick(userId) },
                modifier = Modifier
                    .fillMaxWidth(0.6f) // ajusta a largura (60% da largura disponível)
                    .height(48.dp),     // opcional: altura do botão
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = BlueNavy
                )
            ) {
                Text("Editar Perfil", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilImagePreview() {
    PerfilImage(
        userId = 1,
        userName = "Luiz Ferreira",
        userUsername = "ferreiraluizga",
        onEditClick = {}
    )
}
