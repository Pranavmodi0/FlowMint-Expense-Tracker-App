package com.only.flowmint.models

sealed class Recurrence(val name: String, val target: String) {
    data object None : Recurrence("None", "None")
    data object Daily : Recurrence("Daily", "Today")
    data object Weekly : Recurrence("Weekly", "This Week")
    data object Monthly : Recurrence("Monthly", "This Month")
    data object Yearly : Recurrence("Yearly", "This Year")
}

fun String.toRecurrence() : Recurrence{
    return when(this) {
        "None" -> Recurrence.None
        "Daily" -> Recurrence.Daily
        "Weekly" -> Recurrence.Weekly
        "Monthly" -> Recurrence.Monthly
        "Yearly" -> Recurrence.Yearly
        else -> Recurrence.None
    }
}