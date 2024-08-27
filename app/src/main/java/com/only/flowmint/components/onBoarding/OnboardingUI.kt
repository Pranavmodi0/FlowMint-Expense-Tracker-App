package com.only.flowmint.components.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intuit.sdp.R
import com.only.flowmint.ui.theme.ButtonBackground
import com.only.flowmint.ui.theme.Top


@Composable
fun OnboardingUI(onBoarding: OnBoarding) {

    val gradientShader = listOf(Top, ButtonBackground)


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._60sdp)))

        Image(
            painter = painterResource(id = onBoarding.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(dimensionResource(id = R.dimen._370sdp))
                .padding(horizontal = dimensionResource(id = R.dimen._20sdp), vertical = 0.dp),
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._20sdp)))

        Text(
            text = onBoarding.title,
            fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._24ssp).value.sp,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientShader
                )
            ),
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen._15sdp))

            )

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._10sdp)))
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingUIPreview1() {
    OnboardingUI(onBoarding = OnBoarding.First)
}

@Preview(showBackground = true)
@Composable
fun OnboardingUIPreview2() {
    OnboardingUI(onBoarding = OnBoarding.Second)
}

@Preview(showBackground = true)
@Composable
fun OnboardingUIPreview3() {
    OnboardingUI(onBoarding = OnBoarding.Third)
}