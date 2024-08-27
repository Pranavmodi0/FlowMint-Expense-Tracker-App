package com.only.flowmint.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.only.flowmint.R
import com.only.flowmint.components.ReportPage
import com.only.flowmint.models.Recurrence
import com.only.flowmint.ui.theme.TopAppBarBackground
import com.only.flowmint.viewmodels.ReportsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Report(vm: ReportsViewModel = viewModel()) {
    val uiState = vm.uiState.collectAsState().value

    val recurrences = listOf(
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Reports") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                ),
                actions = {
                    IconButton(onClick = vm::openRecurrenceMenu) {
                        Icon(
                            painterResource(id = R.drawable.calender),
                            contentDescription = "Change recurrence"
                        )
                    }
                    DropdownMenu(
                        expanded = uiState.recurrenceMenuOpened,
                        onDismissRequest = vm::closeRecurrenceMenu
                    ) {
                        recurrences.forEach { recurrence ->
                            DropdownMenuItem(text = { Text(recurrence.name) }, onClick = {
                                vm.setRecurrence(recurrence)
                                vm.closeRecurrenceMenu()
                            })
                        }
                    }
                }
            )
        },

        content = { innerPadding ->
            val numOfPages = when (uiState.recurrence) {
                Recurrence.Weekly -> 53
                Recurrence.Monthly -> 12
                Recurrence.Yearly -> 1
                else -> 53
            }
            val pagerState = rememberPagerState(pageCount = { numOfPages })
            HorizontalPager(state = pagerState, reverseLayout = true) { page ->
                ReportPage(innerPadding, page, uiState.recurrence)
            }
        }
    )
}