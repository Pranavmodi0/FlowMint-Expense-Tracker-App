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
import com.only.flowmint.models.groupByMonth
import com.only.flowmint.ui.theme.LabelSecondary
import com.only.flowmint.utils.simplifyNumber
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun YearlyChart(expenses: List<Expense>) {
    val groupedExpenses = expenses.groupByMonth()

    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                BarChartData.Bar(
                    label = Month.JANUARY.name.substring(0, 1),
                    value = groupedExpenses[Month.JANUARY.name]?.total?.toFloat()
                        ?: 0f,
                    color = Color.White,
                ),
                BarChartData.Bar(
                    label = Month.FEBRUARY.name.substring(0, 1),
                    value = groupedExpenses[Month.FEBRUARY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.MARCH.name.substring(0, 1),
                    value = groupedExpenses[Month.MARCH.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.APRIL.name.substring(0, 1),
                    value = groupedExpenses[Month.APRIL.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.MAY.name.substring(0, 1),
                    value = groupedExpenses[Month.MAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.JUNE.name.substring(0, 1),
                    value = groupedExpenses[Month.JUNE.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.JULY.name.substring(0, 1),
                    value = groupedExpenses[Month.JULY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.AUGUST.name.substring(0, 1),
                    value = groupedExpenses[Month.AUGUST.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.SEPTEMBER.name.substring(0, 1),
                    value = groupedExpenses[Month.SEPTEMBER.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.OCTOBER.name.substring(0, 1),
                    value = groupedExpenses[Month.OCTOBER.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.NOVEMBER.name.substring(0, 1),
                    value = groupedExpenses[Month.NOVEMBER.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                BarChartData.Bar(
                    label = Month.DECEMBER.name.substring(0, 1),
                    value = groupedExpenses[Month.DECEMBER.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
            )
        ),
        labelDrawer = LabelDrawer(recurrence = Recurrence.Yearly),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = LabelSecondary,
            labelValueFormatter = ::simplifyNumber,
            labelRatio = 7,
            labelTextSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp
        ),
        barDrawer = BarDrawer(recurrence = Recurrence.Yearly),
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen._18sdp))
            .fillMaxSize()
    )
}