package com.only.flowmint.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.flowmint.MyApp
import com.only.flowmint.data.ExpensesState
import com.only.flowmint.models.Expense
import com.only.flowmint.models.Recurrence
import com.only.flowmint.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ExpensesViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ExpensesState())
    val uiState: StateFlow<ExpensesState> = _uiState.asStateFlow()

    private val db = MyApp.db

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    expenses = db.query<Expense>().find()
                )
            }
            viewModelScope.launch(Dispatchers.IO) {
                setRecurrence(Recurrence.Daily)
            }
        }
    }

    fun setRecurrence(recurrence: Recurrence){

        val (start, end) = calculateDateRange(recurrence, 0)

        val filteredExpenses = db.query<Expense>().find().filter { expense ->
            (expense.date.toLocalDate().isAfter(start) && expense.date.toLocalDate().isBefore(end))
                    ||  expense.date.toLocalDate().isEqual(start)
                    || expense.date.toLocalDate().isEqual(end)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val sumTotal = filteredExpenses.sumOf { it.amount }

            _uiState.update { currentState ->
                currentState.copy(
                    recurrence = recurrence,
                    expenses = filteredExpenses,
                    sumTotal = sumTotal
                )
            }
        }
    }
}