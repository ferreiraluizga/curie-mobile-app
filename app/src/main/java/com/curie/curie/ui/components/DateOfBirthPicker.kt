package com.curie.curie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.CurieTheme
import com.curie.curie.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthPicker() {
    val context = LocalContext.current
    val datePickerState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<String?>(null) }

    // Abre o diálogo do seletor de data
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    selectedDate = datePickerState.selectedDateMillis?.let { millis ->
                        // Formata a data para dd/MM/yyyy
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        formatter.format(Date(millis))
                    }
                }) {
                    Text("OK", color = BlueNavy)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Campo que exibe a data selecionada
    OutlinedTextField(
        value = selectedDate ?: "",
        onValueChange = {},
        label = { Text("Data de Nascimento") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Selecionar data",
                    tint = BlueNavy
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, Shapes.medium, clip = true)
            .background(Color.White, Shapes.medium)
            .clickable { showDialog = true },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = BlueNavy
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DateOfBirthInputPreview() {
    CurieTheme {
        DateOfBirthPicker()
    }
}