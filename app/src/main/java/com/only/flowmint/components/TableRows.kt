package com.only.flowmint.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.intuit.sdp.R
import com.only.flowmint.ui.theme.Destructive
import com.only.flowmint.ui.theme.TextPrimary
import com.only.flowmint.ui.theme.Typography

@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    label : String? = null,
    hasArrow : Boolean = false,
    icon : Int = 0,
    isDestructive : Boolean = false,
    detailContent: (@Composable RowScope.() -> Unit)? = null,
    content: (@Composable RowScope.() -> Unit)? = null){


    val textColor = if (isDestructive) Destructive else TextPrimary

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = dimensionResource(id = R.dimen._14sdp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (label != null) {
            Text(
                text = label,
                style = Typography.bodyMedium,
                color = textColor,
                modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen._5sdp), vertical = dimensionResource(id = R.dimen._5sdp))
            )
        }
        if (content != null) {
            content()
        }
        if (hasArrow) {
            Icon(
                painterResource(id = icon),
                contentDescription = "Right arrow",
                modifier = modifier.padding(vertical = dimensionResource(id = R.dimen._5sdp)))
        }
        if (detailContent != null) {
            detailContent()
        }
    }
}
