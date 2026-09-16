package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = DarkPrimary,
    onPrimary = NavyDark,
    primaryContainer = NavyLight,
    onPrimaryContainer = PureWhite,
    secondary = DarkGold,
    onSecondary = NavyDark,
    secondaryContainer = GoldDark,
    onSecondaryContainer = PureWhite,
    tertiary = SlateLight,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = PureWhite,
    onSurface = PureWhite,
    outline = DarkBorder,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = NavyPrimary,
    onPrimary = PureWhite,
    primaryContainer = NeutralCard,
    onPrimaryContainer = NavyDark,
    secondary = GoldAccent,
    onSecondary = PureWhite,
    secondaryContainer = Color(0xFFF7EBD2),
    onSecondaryContainer = GoldDark,
    tertiary = SlateSecondary,
    background = OffWhiteSurface,
    surface = PureWhite,
    surfaceVariant = NeutralCard,
    onBackground = NavyDark,
    onSurface = NavyDark,
    outline = BorderSubtle,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

