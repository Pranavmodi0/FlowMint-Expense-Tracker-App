package com.only.flowmint.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(

    primary = Primary,
    background = Surface,
    surface = Surface,
    error = Destructive,
    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = TextPrimary,


    /* Other default colors to override
   background = Color(0xFFFFFBFE),
   surface = Color(0xFFFFFBFE),
   onPrimary = Color.White,
   onSecondary = Color.White,
   onTertiary = Color.White,
   onBackground = Color(0xFF1C1B1F),
   onSurface = Color(0xFF1C1B1F),
   */
)

@Composable
fun RealmAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    val activity = LocalView.current.context as Activity
    val backgroundArgb = Top.toArgb()
    activity.window?.statusBarColor = backgroundArgb

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}