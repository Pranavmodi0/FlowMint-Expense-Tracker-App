package com.only.flowmint.components.onBoarding

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.intuit.sdp.R
import com.only.flowmint.ui.theme.ButtonBackground
import com.only.flowmint.ui.theme.Top
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(onFinish: () -> Unit) {

    val pages = listOf(OnBoarding.First, OnBoarding.Second, OnBoarding.Third)

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    val scope = rememberCoroutineScope()

    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = Black.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen._20sdp),
                            top = dimensionResource(id = R.dimen._10sdp), bottom = dimensionResource(id = R.dimen._30sdp))
                ) {
                    repeat(pages.size){
                        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._2sdp)))

                        Box(modifier = Modifier
                            .height(dimensionResource(id = R.dimen._12sdp))
                            .width(
                                width = if (it == pagerState.currentPage) dimensionResource(id = R.dimen._30sdp) else dimensionResource(
                                    id = R.dimen._15sdp
                                )
                            )
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen._10sdp)))
                            .background(color = if (it == pagerState.currentPage) Top else Color.LightGray)
                        )
                        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen._2sdp)))
                    }
                }
//                IndicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage)

                Box(
                    modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen._20sdp),
                top = dimensionResource(id = R.dimen._10sdp), bottom = dimensionResource(id = R.dimen._30sdp))
                ) {
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage < 2) {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                } else {
                                    onFinish()
                                }
                            }
                        },
                        modifier = Modifier.size(dimensionResource(id = R.dimen._50sdp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonBackground
                        ),
                        border= BorderStroke(dimensionResource(id = R.dimen._1sdp), ButtonBackground),
                        shape = CircleShape
                    ) {
                        Image(
                            painter = painterResource(id = com.only.flowmint.R.drawable.forward_arrow),
                            contentDescription = null,
                            contentScale = ContentScale.None,
                            modifier = Modifier.size(dimensionResource(id = R.dimen._245sdp))
                        )
                    }


//                    OnBoardButton {
//                        scope.launch {
//                            if (pagerState.currentPage < 2) {
//                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                            } else {
//                                onFinish()
//                            }
//                        }
//                    }
                }
            }
        },
        content = {
            Column(Modifier.padding(it)) {
                HorizontalPager(state = pagerState) { index ->
                    OnboardingUI(onBoarding = pages[index])
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen {}
    }