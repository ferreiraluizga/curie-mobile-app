package com.curie.curie.ui.screens.carreira

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curie.curie.ui.theme.BlueNavy

@Composable
fun InstrucoesScreen(
    onStartClick: () -> Unit,
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // Botão voltar
        IconButton(onClick = onBack) {
            Icon(Icons.Outlined.ArrowBack, contentDescription = "Voltar")
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Antes de começar...",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = BlueNavy
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Siga estas instruções para obter resultados precisos:",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(Modifier.height(24.dp))

        InstructionItem(
            icon = Icons.Outlined.Schedule,
            text = "Reserve cerca de 10 minutos sem interrupções."
        )
        InstructionItem(
            icon = Icons.Outlined.TouchApp,
            text = "Responda de forma sincera e espontânea."
        )
        InstructionItem(
            icon = Icons.Outlined.RemoveRedEye,
            text = "Não pense demais: escolha a alternativa que mais combina com você."
        )
        InstructionItem(
            icon = Icons.Outlined.WarningAmber,
            text = "Se você fechar o teste, suas respostas serão perdidas."
        )
        InstructionItem(
            icon = Icons.Outlined.CheckCircle,
            text = "Ao final, você verá um resultado detalhado."
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onStartClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BlueNavy)
        ) {
            Text("Começar agora")
        }
    }
}


@Composable
fun InstructionItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BlueNavy,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 15.sp)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewInstrucoesScreen() {
    InstrucoesScreen(
        onStartClick = {},
        onBack = {}
    )
}