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
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.intuit.sdp.R
import com.only.flowmint.models.Expense
import com.only.flowmint.models.Recurrence
import com.only.flowmint.models.groupByDayOfWeek
import com.only.flowmint.ui.theme.LabelSecondary
import com.only.flowmint.utils.simplifyNumber
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyCharts(expense: List<Expense>) {
    val groupedExpenses = expense.groupByDayOfWeek()

    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                Bar(
                    label = DayOfWeek.MONDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.MONDAY.name]?.total?.toFloat()
                        ?: 0f,
                    color = Color.White,
                ),
                Bar(
                    label = DayOfWeek.TUESDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                Bar(
                    label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                Bar(
                    label = DayOfWeek.THURSDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                Bar(
                    label = DayOfWeek.FRIDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                Bar(
                    label = DayOfWeek.SATURDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
                Bar(
                    label = DayOfWeek.SUNDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.White
                ),
            )
        ),
        labelDrawer = LabelDrawer(recurrence = Recurrence.Weekly),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = LabelSecondary,
            labelValueFormatter = ::simplifyNumber,
            labelRatio = 7,
            labelTextSize = dimensionResource(id = com.intuit.ssp.R.dimen._12ssp).value.sp
        ),
        barDrawer = BarDrawer(recurrence = Recurrence.Weekly),
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen._18sdp))
            .fillMaxSize()
    )
}