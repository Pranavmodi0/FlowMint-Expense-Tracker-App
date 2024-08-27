package com.only.flowmint.components.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.intuit.sdp.R
import com.only.flowmint.ui.theme.Top

@Composable
fun IndicatorUI(
    pageSize: Int,
    currentPage: Int,
    selectColor: Color = Top,
    unselectColor: Color = Color.LightGray
){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pageSize){
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._2sdp)))

            Box(modifier = Modifier.height(dimensionResource(id = R.dimen._12sdp))
                .width(width = if (it == currentPage) dimensionResource(id = R.dimen._30sdp) else dimensionResource(id = R.dimen._15sdp))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen._10sdp)))
                .background(color = if (it == currentPage) selectColor else unselectColor)
            )

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._2sdp)))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview(){
    IndicatorUI(pageSize = 3, currentPage = 0)
}