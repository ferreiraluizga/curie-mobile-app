package com.curie.curie.ui.components.tarefa

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.Color
import com.curie.curie.ui.theme.BlueNavy

@RequiresApi(Build.VERSION_CODES.O)
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

    val displayFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val parseFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")

    LaunchedEffect(initialValue) {
        if (initialValue.isNotBlank()) {
            val raw = initialValue.substringBefore("T")
            try {
                val date = java.time.LocalDate.parse(raw)
                selectedDate = date.format(displayFormatter)
            } catch (_: Exception) { }
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

                            // ❗ INTERPRETA EM UTC, NÃO NO FUSO DO SISTEMA
                            val localDate = java.time.Instant.ofEpochMilli(millis)
                                .atZone(java.time.ZoneOffset.UTC)
                                .toLocalDate()

                            val finalIso = localDate.format(parseFormatter) + "T00:00:00"
                            selectedDate = localDate.format(displayFormatter)

                            onDateSelected(finalIso)
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
            .clickable { showDialog = true },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = BlueNavy,
            focusedIndicatorColor = BlueNavy,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}