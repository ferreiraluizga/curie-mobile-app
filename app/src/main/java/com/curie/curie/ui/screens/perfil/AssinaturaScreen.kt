package com.curie.curie.ui.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AssinaturaScreen(onBack: () -> Unit) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        // 🔙 Setinha de voltar
        IconButton(onClick = { onBack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Assinatura",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ⭐ Caixa da assinatura
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Plano Premium",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Status: Ativo",
                    color = Color(0xFF2E7D32) // verde
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Válida até: 28/12/2025",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* simulação */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Gerenciar assinatura")
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Sua assinatura garante acesso ilimitado às funcionalidades avançadas do Curie.",
            color = Color.DarkGray
        )
    }
}
