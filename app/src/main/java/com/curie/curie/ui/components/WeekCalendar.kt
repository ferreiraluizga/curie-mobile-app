package com.curie.curie.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
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
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekCalendar() {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek.value % 7  // Domingo como 0
    val startOfWeek = today.minusDays(dayOfWeek.toLong()) // início da semana (domingo)

    val weekDates = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        weekDates.forEach { date ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val weekdayShort = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("pt", "BR"))
                Text(weekdayShort, fontSize = 12.sp, color = Color.Gray)
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = if (date == today) BlueNavy else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        color = if (date == today) Color.White else Color.Black,
                        fontWeight = if (date == today) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeekCalendarPreview() {
    CurieTheme {
        WeekCalendar()
    }
}