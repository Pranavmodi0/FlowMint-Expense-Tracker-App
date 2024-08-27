package com.only.flowmint.components.onBoarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.only.flowmint.R
import com.only.flowmint.ui.theme.ButtonBackground

@Composable
fun OnBoardButton(
    backgroundColor: Color = ButtonBackground,
    onClick: () -> Unit
) {

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.size(dimensionResource(id = com.intuit.sdp.R.dimen._50sdp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        border= BorderStroke(dimensionResource(id = com.intuit.sdp.R.dimen._1sdp), backgroundColor),
        shape = CircleShape
    ) {
        Image(
            painter = painterResource(id = R.drawable.forward_arrow),
            contentDescription = null,
            contentScale = ContentScale.None,
            modifier = Modifier.size(dimensionResource(id = com.intuit.sdp.R.dimen._245sdp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardButtonPreview() {
    OnBoardButton(onClick = {})
}