package com.only.flowmint.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.only.flowmint.R
import com.only.flowmint.ui.theme.FillTertiary
import com.only.flowmint.ui.theme.RealmAppTheme
import com.only.flowmint.ui.theme.TextColor
import com.only.flowmint.ui.theme.Top
import com.only.flowmint.ui.theme.Typography

@Composable
fun ExpensePicker(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

//    val gradientShader = listOf(Top, Top2)

    Surface(
        shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._20sdp)),
        onClick = onClick,
        modifier = modifier,
        color = TextColor
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .background(TextColor)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._10sdp),
                    vertical = dimensionResource(id = com.intuit.sdp.R.dimen._2sdp)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    label, style = Typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.unfold_more),
                    contentDescription = "Open Picker",
                    modifier = Modifier.padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._5sdp))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensePickerPreview() {
    RealmAppTheme {
        ExpensePicker("this week", onClick = {})
    }
}