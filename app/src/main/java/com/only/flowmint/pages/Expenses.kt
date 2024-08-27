package com.only.flowmint.pages

import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.only.flowmint.R
import com.only.flowmint.components.ExpensePicker
import com.only.flowmint.components.expenseList.ExpenseList
import com.only.flowmint.models.Recurrence
import com.only.flowmint.ui.theme.Top
import com.only.flowmint.ui.theme.Top2
import com.only.flowmint.ui.theme.Typography
import com.only.flowmint.viewmodels.AuthViewModel
import com.only.flowmint.viewmodels.ExpensesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Expenses(
    vm: ExpensesViewModel = viewModel()
) {

    val gradientShader = listOf(Top, Top2)

    val vms: AuthViewModel = viewModel()
    val uiState by vms.uiState.collectAsState()
    val imageLoader = ImageLoader(LocalContext.current)

    val recurrences = listOf(
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    val state by vm.uiState.collectAsState()
    var recurrenceMenuIcon by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = com.intuit.sdp.R.dimen._200sdp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = gradientShader
                        )
                    ),
            ) {

                Row(
                    modifier = Modifier.padding(start = 30.dp, top = 30.dp, end = 30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = null
                    )

                    for (category in uiState.categories) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(category.profile)
                                .crossfade(true)
                                .build(),
                            imageLoader = imageLoader,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(dimensionResource(id = com.intuit.sdp.R.dimen._30sdp))
                                .clip(CircleShape),
                            alignment = Alignment.TopStart
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(start = 30.dp, top = 40.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "My budget",
                        style = Typography.titleMedium,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(
                            end = dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)
                        )
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 30.dp, top = 10.dp)
                ) {
                    Row {
                        Text(
                            text = "₹",
                            style = Typography.bodySmall,
                            color = Color.White,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(
                                end = dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)
                            )
                        )
                        Text(
                            DecimalFormat("0.#").format(state.sumTotal),
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 50.sp
                        )
                    }
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = gradientShader
                        )
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp),
                                topEnd = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp)
                            )
                        )
                        .background(Color.White)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ExpensePicker(
                            state.recurrence.target,
                            onClick = {
                                recurrenceMenuIcon = !recurrenceMenuIcon
                            },
                            modifier = Modifier.padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp))
                        )
                        DropdownMenu(expanded = recurrenceMenuIcon,
                            onDismissRequest = { recurrenceMenuIcon = false }) {
                            recurrences.forEach { recurrences ->
                                DropdownMenuItem(
                                    text = { Text(recurrences.target) },
                                    onClick = {
                                        vm.setRecurrence(recurrences)
                                        recurrenceMenuIcon = false
                                    }
                                )
                            }
                        }
                    }
                    ExpenseList(
                        expenses = state.expenses,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .background(Color.White)
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun ExpensesPreview(){
    Expenses()
}