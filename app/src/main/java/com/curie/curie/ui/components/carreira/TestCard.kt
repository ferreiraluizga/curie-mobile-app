package com.curie.curie.ui.components.carreira

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme
import com.curie.curie.ui.theme.Typography

@Composable
fun TestCard(
    title: String,
    description: String,
    questions: String,
    buttonText: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // Cabeçalho com ícone redondo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = buttonColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = buttonColor
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                color = Color.DarkGray,
                style = Typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Linha com ícone de relógio e quantidade de perguntas
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = questions,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buttonText, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestCardPreview() {
    CurieTheme {
        Surface(color = Color(0xFFF5F5F5)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TestCard(
                    title = "Teste Vocacional",
                    description = "Descubra suas áreas de afinidade profissional.",
                    questions = "17 perguntas",
                    buttonText = "Explorar carreiras",
                    icon = Icons.Outlined.Book,
                    buttonColor = BlueNavy,
                    onClick = {}
                )
            }
        }
    }
}
