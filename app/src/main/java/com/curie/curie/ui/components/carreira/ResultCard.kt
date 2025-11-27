package com.curie.curie.ui.components.carreira

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.BorderStroke
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.Typography

@Composable
fun ResultCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = color, shape = CircleShape),
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
                    color = color
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color.DarkGray,
                style = Typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = color
                ),
                border = BorderStroke(1.dp, color),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Text(text = buttonText, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultCardPreview() {
    ResultCard(
        title = "Temperamento",
        description = "Seu perfil predominante é Colérico.",
        icon = Icons.Outlined.Psychology,
        color = BlueNavy,
        buttonText = "Ler mais",
        onClick = {}
    )
}