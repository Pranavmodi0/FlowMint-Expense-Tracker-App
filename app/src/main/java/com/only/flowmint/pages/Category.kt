package com.only.flowmint.pages

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.only.flowmint.R
import com.only.flowmint.components.DismissBackground
import com.only.flowmint.components.TableRow
import com.only.flowmint.components.UnstyledTextField
import com.only.flowmint.ui.theme.BackgroundElevated
import com.only.flowmint.ui.theme.DividerHorizontal
import com.only.flowmint.ui.theme.RealmAppTheme
import com.only.flowmint.ui.theme.TopAppBarBackground
import com.only.flowmint.ui.theme.Typography
import com.only.flowmint.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Category(navController: NavHostController, vm : CategoryViewModel = viewModel()) {

    val uiState by vm.uiState.collectAsState()
    val colorPrickerController = rememberColorPickerController()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Category") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                ),
                navigationIcon = {
                    Surface(
                        onClick = navController::popBackStack,
                        color = Color.Transparent
                    ) {
                        Row(modifier = Modifier.padding(vertical = dimensionResource(id = com.intuit.sdp.R.dimen._5sdp))) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Settings"
                            )
                            Text(
                                modifier = Modifier.padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._4sdp)),
                                text = "Settings"
                            )
                        }
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(dimensionResource(id = com.intuit.sdp.R.dimen._14sdp))
                            .clip(RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._5sdp)))
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(
                            uiState.categories,
                            key = { _, category -> category.name }) { index, category ->

                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = {
                                    when (it) {
                                        SwipeToDismissBoxValue.EndToStart -> {
                                            vm.deleteCategory(category)
                                            Toast.makeText(
                                                context,
                                                "${category.name} Deleted",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            true
                                        }

                                        else -> {
                                            false
                                        }
                                    }
                                }
                            )

                            SwipeToDismissBox(
                                state = dismissState,
                                modifier = Modifier.animateItemPlacement(),
                                backgroundContent = { DismissBackground(dismissState) },
                                content = {
                                    TableRow(modifier = Modifier.background(BackgroundElevated)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Surface(
                                                color = category.color,
                                                shape = CircleShape,
                                                border = BorderStroke(
                                                    color = Color.White,
                                                    width = dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)
                                                ),
                                                modifier = Modifier.size(dimensionResource(id = com.intuit.sdp.R.dimen._14sdp)),
                                            ) {}
                                            Text(
                                                text = category.name,
                                                modifier = Modifier.padding(
                                                    horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp),
                                                    vertical = dimensionResource(id = com.intuit.sdp.R.dimen._8sdp)
                                                ),
                                                style = Typography.bodyMedium
                                            )
                                        }
                                    }
                                })
                            if (index != uiState.categories.lastIndex) {
                                Row(
                                    modifier = Modifier
                                        .background(BackgroundElevated)
                                        .height(dimensionResource(id = com.intuit.sdp.R.dimen._1sdp))
                                ) {
                                    HorizontalDivider(
                                        thickness = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
                                        modifier = Modifier.padding(
                                            horizontal = dimensionResource(
                                                id = com.intuit.sdp.R.dimen._8sdp
                                            )
                                        ),
                                        color = DividerHorizontal
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp))
                        .padding(bottom = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp))
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.colorPickerShow) {
                        Dialog(onDismissRequest = { vm.hideColorPicker() }) {
                            Surface(
                                color = BackgroundElevated,
                                shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(all = dimensionResource(id = com.intuit.sdp.R.dimen._28sdp))
                                ) {
                                    Text(text = "Select Color", style = Typography.titleLarge)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._22sdp)),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AlphaTile(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(dimensionResource(id = com.intuit.sdp.R.dimen._58sdp))
                                                .clip(RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._4sdp))),
                                            controller = colorPrickerController
                                        )
                                    }
                                    HsvColorPicker(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(dimensionResource(id = com.intuit.sdp.R.dimen._298sdp))
                                            .padding(dimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
                                        controller = colorPrickerController,
                                        onColorChanged = { envelope ->
                                            vm.setNewCategoryColor(envelope.color)
                                        }
                                    )
                                    TextButton(
                                        onClick = vm::hideColorPicker,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._22sdp))
                                    ) {
                                        Text(text = "Done")
                                    }
                                }
                            }
                        }
                    }
                    Surface(
                        onClick = vm::showColorPicker,
                        shape = CircleShape,
                        border = BorderStroke(
                            width = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
                            color = Color.White
                        ),
                        color = uiState.newCatColor,
                        modifier = Modifier.size(
                            width = dimensionResource(id = com.intuit.sdp.R.dimen._22sdp),
                            height = dimensionResource(id = com.intuit.sdp.R.dimen._22sdp)
                        )
                    ) { }
                    Surface(
                        color = BackgroundElevated,
                        modifier = Modifier
                            .height(dimensionResource(id = com.intuit.sdp.R.dimen._42sdp))
                            .weight(1f)
                            .padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._14sdp)),
                        shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._8sdp)),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            UnstyledTextField(
                                value = uiState.newCategoryName,
                                onValueChange = vm::setNewCategoryName,
                                placeholder = { Text(text = "Category Name") },
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.send),
                        "Create Category",
                        modifier = Modifier.clickable {
                            if (uiState.newCategoryName.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Category Name is Empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                vm.createNewCategory()
                            }
                        }.padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._8sdp))
                            .size(dimensionResource(id = com.intuit.sdp.R.dimen._28sdp))
                    )
                }
            }
        }
    )
}
@Preview(showSystemUi = true)
@Composable
fun CategoryPreview(){
    RealmAppTheme {
        Category(navController = rememberNavController())
    }
}