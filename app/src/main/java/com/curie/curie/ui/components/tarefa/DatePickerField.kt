package com.curie.curie.ui.components.tarefa

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.curie.curie.ui.theme.BlueNavy
import com.curie.curie.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    initialValue: String = "",
    onDateSelected: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }

    // Formatadores
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val displayFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Atualiza display quando recebe valor inicial (edição)
    LaunchedEffect(initialValue) {
        if (initialValue.isNotBlank()) {
            val rawDate = initialValue.substringBefore("T") // evita crash
            try {
                val parsed = inputFormatter.parse(rawDate)
                selectedDate = displayFormatter.format(parsed!!)
            } catch (_: Exception) {
                selectedDate = ""
            }
        }
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        val millis = datePickerState.selectedDateMillis

                        if (millis != null) {
                            val formatted = inputFormatter.format(Date(millis))
                            val finalValue = "${formatted}T00:00"

                            // exibir amigável (dd/MM/yyyy)
                            selectedDate = displayFormatter.format(Date(millis))

                            onDateSelected(finalValue)
                        }
                    }
                ) {
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

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    Icons.Filled.CalendarMonth,
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
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = BlueNavy
        )
    )
}
