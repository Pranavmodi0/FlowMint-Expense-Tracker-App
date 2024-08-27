package com.only.flowmint.components.onBoarding

import androidx.annotation.DrawableRes
import com.only.flowmint.R


sealed class OnBoarding(
    @DrawableRes val image: Int,
    val title: String
) {
    data object First : OnBoarding(
        image = R.drawable.screen_1,
        title = "welcome your\nfinancial journey\nstarts here"
    )

    data object Second : OnBoarding(
        image = R.drawable.screen_2,
        title = "take control of\nyour finances with ease"
    )

    data object Third : OnBoarding(
        image = R.drawable.screen_3,
        title = "pay later for\r\nthe things you love"
    )
}