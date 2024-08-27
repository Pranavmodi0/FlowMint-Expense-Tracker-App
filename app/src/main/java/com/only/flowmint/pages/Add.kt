package com.only.flowmint.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intuit.sdp.R
import com.only.flowmint.components.TableRow
import com.only.flowmint.components.UnstyledTextField
import com.only.flowmint.models.DateManage
import com.only.flowmint.models.Recurrence
import com.only.flowmint.ui.theme.BackgroundElevated
import com.only.flowmint.ui.theme.DividerHorizontal
import com.only.flowmint.ui.theme.TopAppBarBackground
import com.only.flowmint.viewmodels.AddViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Add(vmAdd: AddViewModel = viewModel()) {

    val state by vmAdd.uiState.collectAsState()

    val recurrences = listOf(
        Recurrence.None,
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    val dateState = rememberDatePickerState()
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateManage().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateManage().dateToString(millisToLocalDate)
    } ?: "${LocalDate.now()}"

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Add") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                )
            )
        },
        content = { padding ->

            Column(modifier = Modifier.padding(padding)) {
                Column(modifier = Modifier
                    .padding(dimensionResource(id = R.dimen._14sdp))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen._5sdp)))
                    .background(BackgroundElevated)
                    .fillMaxWidth()
                ) {
                    TableRow(label = "Amount", detailContent = {
                        UnstyledTextField(
                            value = state.amount,
                            onValueChange = vmAdd::setAmount,
                            placeholder = {Text(text = "Add Amount")},
                            arrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            textStyle = TextStyle(
                                textAlign = TextAlign.End
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    })
                    HorizontalDivider(thickness = dimensionResource(id = R.dimen._1sdp), color = DividerHorizontal)
                    TableRow(label = "Recurrence", detailContent = {
                        var recurrenceMenu by remember {
                            mutableStateOf(false)
                        }
                        TextButton(onClick = { recurrenceMenu = true }) {
                            Text(state.recurrence.name)
                            DropdownMenu(
                                modifier = Modifier.background(Color.Transparent),
                                expanded = recurrenceMenu,
                                onDismissRequest = { recurrenceMenu = false}){
                                recurrences.forEach { recurrences ->
                                    DropdownMenuItem(
                                        text = { Text(recurrences.name) },
                                        onClick = {
                                            vmAdd.setRecurrence(recurrences)
                                            recurrenceMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    })
                    HorizontalDivider(thickness = dimensionResource(id = R.dimen._1sdp), color = DividerHorizontal)

                    var datePickerShowing by remember {
                        mutableStateOf(false)
                    }
                    TableRow(label = "Date", detailContent = {
                        TextButton(
                            onClick = {
                                datePickerShowing = true
                            },
                        ) {
                            Text(text = dateToString)
                            if (datePickerShowing) {
                                DatePickerDialog(
                                    onDismissRequest = { datePickerShowing = false },
                                    confirmButton = {
                                        Button(
                                            onClick = { datePickerShowing = false
                                                millisToLocalDate?.let { vmAdd.setDate(it) }
                                            }
                                        ) {
                                            Text(text = "OK")
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = { datePickerShowing = false }
                                        ) {
                                            Text(text = "Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(state = dateState, showModeToggle = true)
                                }
                            }
                        }
                    })
                    HorizontalDivider(thickness = dimensionResource(id = R.dimen._1sdp), color = DividerHorizontal)
                    TableRow(label = "Note", detailContent = {
                        UnstyledTextField(
                            value = state.note,
                            onValueChange = vmAdd::setNote,
                            placeholder = {Text(text = "Leave a note")},
                            arrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                textAlign = TextAlign.End
                            ),
                        )
                    })
                    HorizontalDivider(thickness = dimensionResource(id = R.dimen._1sdp), color = DividerHorizontal)
                    TableRow(label = "Category", detailContent = {
                        LaunchedEffect(Unit) {
                            vmAdd.refreshCategories()
                        }
                        var categoryMenu by remember {
                            mutableStateOf(false)
                        }
                        TextButton(onClick = { categoryMenu = true }) {
                            Text(
                                state.category?.name ?: "Select a Category",
                                color = state.category?.color ?: Color.White)
                            DropdownMenu(
                                modifier = Modifier.background(Color.Transparent),
                                expanded = categoryMenu,
                                onDismissRequest = { categoryMenu = false}){
                                state.categories?.forEach { categories ->
                                    DropdownMenuItem(
                                        text = {
                                            Row {
                                                Surface(
                                                    modifier = Modifier
                                                        .size(dimensionResource(id = R.dimen._7sdp))
                                                        .align(Alignment.CenterVertically),
                                                    shape = CircleShape,
                                                    color = categories.color
                                                )
                                                {}
                                                Text(
                                                    categories.name,
                                                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen._5sdp))
                                                )
                                            }
                                        },
                                        onClick = {
                                            vmAdd.setCategory(categories)
                                            categoryMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    })
                }

                Button(
                    onClick = vmAdd::submitExpense,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = dimensionResource(id = R.dimen._5sdp)),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen._5sdp)),
                    enabled = state.category != null && state.amount != null
                    ) {
                    Text(text = "Submit expense")
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun AddPreview(){
        Add()
}