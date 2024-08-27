package com.only.flowmint.components

import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intuit.sdp.R
import com.only.flowmint.models.DayExpenses
import com.only.flowmint.ui.theme.LabelSecondary
import com.only.flowmint.ui.theme.TextColor
import com.only.flowmint.ui.theme.Typography
import com.only.flowmint.ui.theme.price
import com.only.flowmint.utils.formatDay
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpensesGroup(date: LocalDate, dayExpenses: DayExpenses, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(15.dp)) {
        Text(
            date.formatDay(),
            style = Typography.headlineMedium,
            color = TextColor,
            fontSize = 20.sp
        )

        HorizontalDivider(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen._7sdp),
                bottom = dimensionResource(id = R.dimen._2sdp)
            ),
            color = Color.White
        )

        dayExpenses.expenses.forEach { expense ->
            ExpenseRow(
                expense = expense,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen._10sdp))
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen._14sdp),
                bottom = dimensionResource(id = R.dimen._2sdp)
            ),
            color = Color.White,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = Typography.headlineMedium,
                color = TextColor,
                fontSize = 16.sp
            )

            Text(
                DecimalFormat("RS 0.#").format(dayExpenses.total),
                style = Typography.headlineMedium,
                color = price
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun ExpensesGroupPreview(){
    ExpensesGroup(date = LocalDate.now(), dayExpenses = (DayExpenses(mutableListOf(), 0.0)))
}