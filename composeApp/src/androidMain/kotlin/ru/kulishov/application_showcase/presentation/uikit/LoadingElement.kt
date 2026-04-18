package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun LoadingElement(){
    val infiniteTransition = rememberInfiniteTransition()
    val targetColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.3f),
        targetValue = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(Modifier.fillMaxSize().background(targetColor))
}