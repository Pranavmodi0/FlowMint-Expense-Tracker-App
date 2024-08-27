package com.only.flowmint.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.intuit.sdp.R
import com.only.flowmint.data.Category
import com.only.flowmint.ui.theme.Typography

@Composable
fun CategoryBatch(category: Category, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen._6sdp)),
        color = category.color.copy(alpha = 0.25f)
    ) {
        Text(
            text = category.name,
            color = category.color,
            style = Typography.bodySmall,
            modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen._5sdp), vertical = dimensionResource(id = R.dimen._1sdp)),
        )
    }
}