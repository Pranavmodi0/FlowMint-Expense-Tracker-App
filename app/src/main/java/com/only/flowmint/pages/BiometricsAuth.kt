package com.only.flowmint.pages

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.only.flowmint.R
import com.only.flowmint.ui.theme.AlertButton
import com.only.flowmint.ui.theme.AlertText
import com.only.flowmint.ui.theme.RealmAppTheme
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

private lateinit var executor: Executor
private lateinit var biometricPrompt: BiometricPrompt
private lateinit var promptInfo: BiometricPrompt.PromptInfo

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BiometricsAuth(navController: NavController) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var showConfirmation by remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = Color.Black.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

    RealmAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.signin_logo),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(dimensionResource(id = com.intuit.sdp.R.dimen._120sdp))
            )
        }
    }

    val biometricManager = remember {
        BiometricManager.from(context)
    }

    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS ->
            Log.d("MY_APP_TAG", "App can authenticate using biometrics.")

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
            Log.e("MY_APP_TAG", "No biometric features available on this device.")

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
            Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // Prompts the user to create credentials that your app accepts.
            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                )
            }
            ActivityCompat.startActivityForResult(
                context as FragmentActivity,
                enrollIntent,
                100,
                null
            )
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            TODO()
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            TODO()
        }

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            TODO()
        }
    }

    executor = remember { ContextCompat.getMainExecutor(context) }
    biometricPrompt = BiometricPrompt(context as FragmentActivity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                showConfirmation = true
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                scope.launch {
                    navController.popBackStack()
                    navController.navigate("bottom_nav")
                }
            }

//            override fun onAuthenticationFailed() {
//                super.onAuthenticationFailed()
//                showConfirmation = true
//            }
        })

    val hasDeviceCredential = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        biometricManager.canAuthenticate(DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
    } else {
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.isDeviceSecure
    }

    val allowedAuthenticators = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (hasDeviceCredential) {
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        } else {
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        }
    } else {
        DEVICE_CREDENTIAL // Use only strong biometrics on API 29 and below
    }

    val promptInfos = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Unlock FlowMint")
        .setSubtitle("Unlock your screen with PIN, pattern, password, face,or fingerprint")
        .setAllowedAuthenticators(allowedAuthenticators)

    if ((allowedAuthenticators and BIOMETRIC_STRONG) == 0) {
        promptInfos.setNegativeButtonText("Cancel")
    }
    promptInfo = promptInfos.build()

    LaunchedEffect(key1 = Unit) {
        biometricPrompt.authenticate(promptInfo)
    }

    if (showConfirmation) {
        AlertDialog(
            containerColor = Color.DarkGray,
            onDismissRequest = { showConfirmation = false },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.alert_lock),
                    contentDescription = null
                )
            },
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FlowMint is locked",
                        color = AlertText
                    )
                }
            },
            text = {
                Text(
                    text = "For your security, you can only use FlowMint when it's unlocked",
                    fontSize = dimensionResource(id = com.intuit.ssp.R.dimen._14ssp).value.sp,
                    color = AlertText
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    biometricPrompt.authenticate(promptInfo)
                    showConfirmation = false
                }) {
                    Text(
                        text = "Unlock",
                        color = AlertButton
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    context.finish()
                }) {
                    Text(
                        text = "Cancel",
                        color = AlertButton
                    )
                }
            })
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showSystemUi = true)
@Composable
fun BiometricsAuthPreview() {
    RealmAppTheme {
        BiometricsAuth(navController = rememberNavController())
    }
}