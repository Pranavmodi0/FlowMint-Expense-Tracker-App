package com.only.flowmint.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.only.flowmint.MyApp
import com.only.flowmint.R
import com.only.flowmint.components.TableRow
import com.only.flowmint.data.Category
import com.only.flowmint.models.Expense
import com.only.flowmint.ui.theme.BackgroundElevated
import com.only.flowmint.ui.theme.DividerHorizontal
import com.only.flowmint.ui.theme.TopAppBarBackground
import com.only.flowmint.viewmodels.AuthViewModel
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch

private val db = MyApp.db

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navController: NavController,
    onClick: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    var deleteConfirmation by remember {
        mutableStateOf(false)
    }

    val imageLoader = ImageLoader(LocalContext.current)

    val vm : AuthViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()

    val eraseAllData: () -> Unit = {
        coroutineScope.launch {
            db.write{
                val expenses = this.query<Expense>().find()
                val category = this.query<Category>().find()
                delete(expenses)
                delete(category)

                deleteConfirmation = false
            }
        }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Settings") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                )
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Row(
                    modifier = Modifier
                        .padding(dimensionResource(id = com.intuit.sdp.R.dimen._14sdp))
                ) {
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
                                .size(dimensionResource(id = com.intuit.sdp.R.dimen._85sdp))
                                .clip(CircleShape),
                        )
                    }
                    Column(
                        modifier = Modifier.padding(
                            top = dimensionResource(id = com.intuit.sdp.R.dimen._25sdp),
                            start = dimensionResource(id = com.intuit.sdp.R.dimen._25sdp)
                        )
                    ) {
                        for (category in uiState.categories) {
                            Text(text = category.email)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = com.intuit.sdp.R.dimen._13sdp))
                        .clip(RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._5sdp)))
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                    TableRow(
                        label = "Category",
                        icon = R.drawable.chevron_right,
                        hasArrow = true,
                        modifier = Modifier.clickable {
                            navController.navigate("category")
                        })
                    HorizontalDivider(
                        thickness = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
                        color = DividerHorizontal
                    )
                    TableRow(
                        label = "Sign Out",
                        icon = R.drawable.logout_icon,
                        hasArrow = true,
                        modifier = Modifier.clickable {
                            onClick()
                        })
                    HorizontalDivider(
                        thickness = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
                        color = DividerHorizontal
                    )
                    TableRow(
                        label = "Erase all data",
                        isDestructive = true,
                        modifier = Modifier.clickable {
                            deleteConfirmation = true
                        })
                    if (deleteConfirmation) {
                        AlertDialog(
                            onDismissRequest = { deleteConfirmation = false },
                            title = { Text(text = "Delete All Data") },
                            text = { Text(text = "Are you sure you want to delete all data?") },
                            confirmButton = {
                                TextButton(onClick = eraseAllData) {
                                    Text(text = "Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { deleteConfirmation = false }) {
                                    Text(text = "Cancel")
                                }
                            })
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview(){
    Settings(navController = rememberNavController()) {}
}