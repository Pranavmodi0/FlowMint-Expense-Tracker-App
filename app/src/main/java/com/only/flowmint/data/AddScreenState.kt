package com.only.flowmint.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.only.flowmint.models.Recurrence
import io.realm.kotlin.query.RealmResults
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class AddScreenState(
    val amount: String = "",
    val recurrence: Recurrence = Recurrence.None,
    val date: LocalDate = LocalDate.now(),
    val note: String = "",
    val category: Category? = null,
    val categories: RealmResults<Category>? = null
)
