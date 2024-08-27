package com.only.flowmint.components

import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.intuit.sdp.R
import com.only.flowmint.models.Expense
import com.only.flowmint.ui.theme.LabelSecondary
import com.only.flowmint.ui.theme.TextColor
import com.only.flowmint.ui.theme.Typography
import com.only.flowmint.ui.theme.price
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseRow(expense: Expense, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                expense.notes,
                style = Typography.headlineMedium,
                color = TextColor
            )
            Text(
                "RS ${DecimalFormat("0.#").format(expense.amount)}",
                style = Typography.headlineMedium,
                color = price
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = dimensionResource(id = R.dimen._4sdp)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryBatch(category = expense.category!!)
            expense.date.run {
                Text(
                    format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = Typography.headlineMedium,
                    color = TextColor
                )
            }
        }
    }
}