package com.only.flowmint.viewmodels

import androidx.lifecycle.ViewModel
import com.only.flowmint.data.ReportsState
import com.only.flowmint.models.Recurrence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReportsViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ReportsState())
    val uiState: StateFlow<ReportsState> = _uiState.asStateFlow()

    fun setRecurrence(recurrence: Recurrence) {
        _uiState.update { currentState ->
            currentState.copy(
                recurrence = recurrence
            )
        }
    }

    fun openRecurrenceMenu() {
        _uiState.update { currentState ->
            currentState.copy(
                recurrenceMenuOpened = true
            )
        }
    }

    fun closeRecurrenceMenu() {
        _uiState.update { currentState ->
            currentState.copy(
                recurrenceMenuOpened = false
            )
        }
    }
}