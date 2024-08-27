package com.only.flowmint.components.charts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.intuit.sdp.R
import com.only.flowmint.models.Expense
import com.only.flowmint.models.Recurrence
import com.only.flowmint.models.groupByDayOfMonth
import com.only.flowmint.ui.theme.LabelSecondary
import com.only.flowmint.utils.simplifyNumber
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyChart(expenses: List<Expense>, month: LocalDate){
    val groupedExpenses = expenses.groupByDayOfMonth()
    val numberOfDays = YearMonth.of(month.year, month.month).lengthOfMonth()

    BarChart(
        barChartData = BarChartData(
            bars = buildList() {
                for (i in 1..numberOfDays) {
                    add(BarChartData.Bar(
                        label = "$i",
                        value = groupedExpenses[i]?.total?.toFloat()
                            ?: 0f,
                        color = Color.White,
                    ))
                }
            }
        ),
        labelDrawer = LabelDrawer(recurrence = Recurrence.Monthly, lastDay = numberOfDays),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = LabelSecondary,
            labelValueFormatter = ::simplifyNumber,
            labelRatio = 7,
            labelTextSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp
        ),
        barDrawer = BarDrawer(recurrence = Recurrence.Monthly),
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen._18sdp))
            .fillMaxSize()
    )
}