package com.only.flowmint

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.only.flowmint.components.onBoarding.OnBoardingScreen
import com.only.flowmint.navigation.NavGraph
import com.only.flowmint.ui.theme.RealmAppTheme
import com.only.flowmint.utils.OnBoardingUtils
import com.only.flowmint.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.launch


class MainActivity : FragmentActivity() {

    private val createOnBoarding by lazy { OnBoardingUtils(this) }
    private val viewModel by viewModels<SplashScreenViewModel>()

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.keepSplashScreenOnScreen.value
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RealmAppTheme {
                if (createOnBoarding.isOnboarding()){
                    NavGraph(navController = rememberNavController())
                } else {
                    ShowOnBoarding()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    private fun ShowOnBoarding() {
        val scope = rememberCoroutineScope()
        OnBoardingScreen {
            createOnBoarding.setOnboarding()
            scope.launch {
                setContent{
                    NavGraph(navController = rememberNavController())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RealmAppTheme {
    }
}