package com.curie.curie.ui.components.tarefa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curie.curie.ui.theme.BlueNavy

@Composable
fun TabsSection(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val tabs = listOf("Tarefas", "Metas")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        tabs.forEach { tab ->
            val isSelected = selectedTab == tab
            Button(
                onClick = { onTabSelected(tab) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) BlueNavy else Color.LightGray,
                    contentColor = if (isSelected) Color.White else Color.DarkGray
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            ) {
                Text(tab)
            }
        }
    }
}
