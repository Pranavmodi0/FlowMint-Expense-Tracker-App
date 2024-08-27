package com.only.flowmint.pages

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import com.only.flowmint.R
import com.only.flowmint.ui.theme.ButtonBackground
import com.only.flowmint.ui.theme.RealmAppTheme
import com.only.flowmint.ui.theme.Top
import kotlinx.coroutines.launch

@Composable
fun Login(
    onClick: () -> Unit,
    loading: Boolean = false
) {

    val gradientShader = listOf(Top, ButtonBackground)

    val viewModelScope = rememberCoroutineScope()

    val andColor = listOf(White, White)

    val context = LocalContext.current
    val view = LocalView.current

    val terms = "https://sites.google.com/view/flowmintterms/home"
    val privacy = "https://sites.google.com/view/flowmint/home"

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = Black.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Black
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome To FlowMint",
                fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._25ssp).value.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientShader
                    )
                ),
                modifier = Modifier.padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._100sdp)),
                textAlign = TextAlign.Center
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    bottom = dimensionResource(id = com.intuit.sdp.R.dimen._110sdp),
                    end = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._70sdp))
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    bottom = dimensionResource(id = com.intuit.sdp.R.dimen._100sdp),
                    start = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.piggy),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._70sdp))
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._40sdp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._90sdp))
                    .clip(CircleShape)
            )
        }
        Row(
            modifier = Modifier.padding(bottom = dimensionResource(id = com.intuit.sdp.R.dimen._130sdp)),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.clickable {
                    viewModelScope.launch {
                        onClick()
                    }
                },
                painter = painterResource(id = R.drawable.google_signin_logo),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = dimensionResource(id = com.intuit.sdp.R.dimen._50sdp)),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "By continuing you agree our",
                fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._8ssp).value.sp,
                color = White,
            )
            Row {
                Text(
                    modifier = Modifier.clickable {
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(terms))
                    },
                    text = "Terms of services",
                    fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._8ssp).value.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = gradientShader
                        )
                    ),
                )

                Spacer(modifier = Modifier.padding(start = dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)))

                Text(
                    text = "&",
                    fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._8ssp).value.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = andColor
                        )
                    )
                )

                Spacer(modifier = Modifier.padding(end = dimensionResource(id = com.intuit.sdp.R.dimen._3sdp)))

                Text(
                    modifier = Modifier.clickable {
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(privacy))
                    },
                    text = "Privacy policy",
                    fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._8ssp).value.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = gradientShader
                        )
                    ),
                )
            }
        }

        if (loading) {
            Dialog(
                onDismissRequest = { !loading },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(dimensionResource(id = com.intuit.sdp.R.dimen._90sdp))
                        .background(
                            White,
                            shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._4sdp))
                        )
                ) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(dimensionResource(id = com.intuit.sdp.R.dimen._18sdp))
                            .width(dimensionResource(id = com.intuit.sdp.R.dimen._18sdp)),
                        strokeWidth = dimensionResource(id = com.intuit.sdp.R.dimen._1sdp),
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = com.intuit.sdp.R.dimen._19sdp)))

                    Text(
                        text = "Please wait..",
                        color = Black,
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = com.intuit.sdp.R.dimen._18sdp))
                            .align(Alignment.BottomCenter),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    RealmAppTheme {
        Login({})
    }
}