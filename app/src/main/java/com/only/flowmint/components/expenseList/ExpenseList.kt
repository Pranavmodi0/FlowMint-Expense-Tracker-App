package com.only.flowmint.components.expenseList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.intuit.sdp.R
import com.only.flowmint.components.ExpensesGroup
import com.only.flowmint.models.Expense
import com.only.flowmint.models.groupByDay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseList(expenses: List<Expense>, modifier: Modifier = Modifier) {
    val groupedExpenses = expenses.groupByDay()

    Column(modifier = modifier) {
        if (groupedExpenses.isEmpty()) {
            Text(text = "No Data for selected date range.",
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen._30sdp)))
        } else {
            groupedExpenses.keys.forEach { date ->
                if (groupedExpenses[date] != null) {
                    ExpensesGroup(
                        date = date,
                        dayExpenses = groupedExpenses[date]!!,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen._20sdp))
                    )
                }
            }
        }
    }
}