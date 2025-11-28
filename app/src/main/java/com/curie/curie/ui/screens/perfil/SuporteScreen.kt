package com.curie.curie.ui.screens.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SuporteScreen(onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔙 Setinha de voltar
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Suporte",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Dúvidas Frequentes",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Lista de FAQs simulada
        FAQItem(
            pergunta = "Como altero meus dados pessoais?",
            resposta = "Acesse a aba de Perfil e toque em Editar informações."
        )

        FAQItem(
            pergunta = "Como altero minha senha?",
            resposta = "Na tela de Perfil, selecione Alterar senha e siga os passos."
        )

        FAQItem(
            pergunta = "Como cancelo minha assinatura?",
            resposta = "Você pode solicitar o cancelamento na área de Assinatura."
        )

        FAQItem(
            pergunta = "O app está lento. O que fazer?",
            resposta = "Verifique sua conexão ou tente reiniciar o aplicativo."
        )

        FAQItem(
            pergunta = "Como entro em contato com o suporte?",
            resposta = "Envie um e-mail para suporte@curieapp.com (simulação)."
        )
    }
}

// Componente reutilizável de FAQ
@Composable
fun FAQItem(pergunta: String, resposta: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {

        Text(
            text = pergunta,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = resposta,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(12.dp))
        Divider()
    }
}
