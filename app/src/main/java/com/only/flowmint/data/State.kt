package com.only.flowmint.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.only.flowmint.models.Expense
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class State(
    val expenses: List<Expense> = listOf(),
    val dateStart: LocalDateTime = LocalDateTime.now(),
    val dateEnd: LocalDateTime = LocalDateTime.now(),
    val avgPerDay: Double = 0.0,
    val totalInRange: Double = 0.0
)
