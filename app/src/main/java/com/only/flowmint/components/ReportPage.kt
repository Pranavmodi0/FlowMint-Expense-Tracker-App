package com.only.flowmint.components

import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intuit.sdp.R
import com.only.flowmint.components.charts.MonthlyChart
import com.only.flowmint.components.charts.WeeklyCharts
import com.only.flowmint.components.charts.YearlyChart
import com.only.flowmint.components.expenseList.ExpenseList
import com.only.flowmint.models.Recurrence
import com.only.flowmint.ui.theme.LabelSecondary
import com.only.flowmint.ui.theme.Typography
import com.only.flowmint.utils.formatDayForRange
import com.only.flowmint.viewmodels.ReportPageViewModel
import com.only.flowmint.viewmodels.viewModelFactory
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportPage(
    innerPadding: PaddingValues,
    page: Int,
    recurrence: Recurrence,
    vm: ReportPageViewModel = viewModel(
        key = "$page-${recurrence.name}",
        factory = viewModelFactory {
            ReportPageViewModel(page, recurrence)
        })
) {
    val uiState = vm.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = dimensionResource(id = R.dimen._14sdp))
            .padding(top = dimensionResource(id = R.dimen._15sdp))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    "${
                        uiState.dateStart.formatDayForRange()
                    } - ${uiState.dateEnd.formatDayForRange()}",
                    style = Typography.titleSmall
                )
                Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._2sdp))) {
                    Text(
                        "RS",
                        style = Typography.bodyMedium,
                        color = LabelSecondary,
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen._2sdp))
                    )
                    Text(
                        DecimalFormat("0.#").format(uiState.totalInRange),
                        style = Typography.headlineMedium
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Avg/day", style = Typography.titleSmall)
                Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen._2sdp))) {
                    Text(
                        "RS",
                        style = Typography.bodyMedium,
                        color = LabelSecondary,
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen._2sdp))
                    )
                    Text(
                        DecimalFormat("0.#").format(uiState.avgPerDay),
                        style = Typography.headlineMedium
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen._170sdp))
                .padding(vertical = dimensionResource(id = R.dimen._14sdp))
        ) {
            when (recurrence) {
                Recurrence.Weekly -> WeeklyCharts(expense = uiState.expenses)
                Recurrence.Monthly -> MonthlyChart(
                    expenses = uiState.expenses,
                    LocalDate.now()
                )

                Recurrence.Yearly -> YearlyChart(expenses = uiState.expenses)
                else -> Unit
            }
        }

        ExpenseList(
            expenses = uiState.expenses, modifier = Modifier
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                )
        )
    }
}