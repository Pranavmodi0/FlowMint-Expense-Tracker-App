package com.only.flowmint.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.only.flowmint.models.Expense
import com.only.flowmint.models.Recurrence


@RequiresApi(Build.VERSION_CODES.O)
data class ExpensesState(
    val recurrence: Recurrence = Recurrence.Daily,
    val sumTotal: Double = 1250.98,
    val expenses: List<Expense> = listOf()
)
