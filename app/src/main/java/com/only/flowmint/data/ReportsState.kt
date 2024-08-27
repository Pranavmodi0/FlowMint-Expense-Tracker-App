package com.only.flowmint.data

import com.only.flowmint.models.Recurrence

data class ReportsState (
    val recurrence: Recurrence = Recurrence.Weekly,
    val recurrenceMenuOpened: Boolean = false
)