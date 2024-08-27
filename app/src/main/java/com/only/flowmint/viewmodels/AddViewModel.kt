package com.only.flowmint.viewmodels

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.flowmint.MyApp
import com.only.flowmint.data.AddScreenState
import com.only.flowmint.data.Category
import com.only.flowmint.models.Expense
import com.only.flowmint.models.Recurrence
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class AddViewModel : ViewModel(){

    @SuppressLint("NewApi")
    private val _uiState = MutableStateFlow(AddScreenState())

    val uiState: StateFlow<AddScreenState> = _uiState.asStateFlow()

    private val db = MyApp.db


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    categories = db.query<Category>().find()
                )
            }
        }
    }

    fun setAmount(amount: String){
//        var parsed = amount.toDoubleOrNull()
//
//        if (amount.isEmpty()){
//            parsed = 0.0
//        }
//        if (parsed != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    amount = amount.trim().ifEmpty { "0" },
                )
            }
//        }
    }

    fun setRecurrence(recurrence: Recurrence){
        _uiState.update { currentState ->
            currentState.copy(
                recurrence = recurrence
            )
        }
    }

    fun setDate(date: LocalDate){
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setNote(note: String){
        _uiState.update { currentState ->
            currentState.copy(
                note = note
            )
        }
    }

    fun setCategory(category: Category){
        _uiState.update { currentState ->
            currentState.copy(
                category = category
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitExpense() {
        if (_uiState.value.categories != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val now = LocalDateTime.now()
                db.write {
                    this.copyToRealm(
                        Expense(
                            _uiState.value.amount.toDouble(),
                            _uiState.value.recurrence,
                            _uiState.value.date.atTime(now.hour, now.minute, now.second),
                            _uiState.value.note,
                            this.query<Category>("id == $0", _uiState.value.category!!.id)
                                .find().first()
                        )
                    )
                }
                _uiState.update { currentState ->
                    currentState.copy(
                        amount  = "",
                        recurrence = Recurrence.None,
                        date = LocalDate.now(),
                        note = "",
                        category = null,
                        categories = null
                    )
                }
            }
        }
    }

    fun refreshCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    categories = db.query<Category>().find()
                )
            }
        }
    }
}