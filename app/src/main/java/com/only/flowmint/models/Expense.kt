package com.only.flowmint.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.only.flowmint.data.Category
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class Expense(): RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var amount: Double = 0.0

    private var _recurrenceName: String = "None"
    val recurrence: Recurrence get() { return _recurrenceName.toRecurrence() }

    private var _dateValue: String = LocalDateTime.now().toString()
    val date: LocalDateTime get() { return LocalDateTime.parse(_dateValue) }

    var notes: String = ""
    var category: Category? = null

    constructor(
        amount: Double,
        recurrence: Recurrence,
        date: LocalDateTime,
        note : String,
        category: Category,
    ) : this() {
        this.amount = amount
        this._recurrenceName = recurrence.name
        this._dateValue = date.toString()
        this.notes = note
        this.category = category
    }
}

data class DayExpenses(
    val expenses: MutableList<Expense>,
    var total: Double,
)

@RequiresApi(Build.VERSION_CODES.O)
fun List<Expense>.groupByDay(): Map<LocalDate, DayExpenses> {
    val dataMap: MutableMap<LocalDate, DayExpenses> = mutableMapOf()

    this.forEach { expense ->
        val date = expense.date.toLocalDate()

        if (dataMap[date] == null) {
            dataMap[date] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[date]!!.expenses.add(expense)
        dataMap[date]!!.total = dataMap[date]!!.total.plus(expense.amount)
    }

    dataMap.values.forEach { dayExpenses ->
        dayExpenses.expenses.sortBy { expense -> expense.date }
    }

    return dataMap.toSortedMap(compareByDescending { it })
}

@RequiresApi(Build.VERSION_CODES.O)
fun List<Expense>.groupByDayOfWeek(): Map<String, DayExpenses> {
    val dataMap: MutableMap<String, DayExpenses> = mutableMapOf()

    this.forEach { expense ->
        val dayOfWeek = expense.date.toLocalDate().dayOfWeek

        if (dataMap[dayOfWeek.name] == null) {
            dataMap[dayOfWeek.name] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[dayOfWeek.name]!!.expenses.add(expense)
        dataMap[dayOfWeek.name]!!.total = dataMap[dayOfWeek.name]!!.total.plus(expense.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}

@RequiresApi(Build.VERSION_CODES.O)
fun List<Expense>.groupByDayOfMonth(): Map<Int, DayExpenses> {
    val dataMap: MutableMap<Int, DayExpenses> = mutableMapOf()

    this.forEach { expense ->
        val dayOfMonth = expense.date.toLocalDate().dayOfMonth

        if (dataMap[dayOfMonth] == null) {
            dataMap[dayOfMonth] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[dayOfMonth]!!.expenses.add(expense)
        dataMap[dayOfMonth]!!.total = dataMap[dayOfMonth]!!.total.plus(expense.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}

@RequiresApi(Build.VERSION_CODES.O)
fun List<Expense>.groupByMonth(): Map<String, DayExpenses> {
    val dataMap: MutableMap<String, DayExpenses> = mutableMapOf()

    this.forEach { expense ->
        val month = expense.date.toLocalDate().month

        if (dataMap[month.name] == null) {
            dataMap[month.name] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[month.name]!!.expenses.add(expense)
        dataMap[month.name]!!.total = dataMap[month.name]!!.total.plus(expense.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}