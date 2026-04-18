package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import ru.kulishov.application_showcase.presentation.navigation.NavigationElement
import ru.kulishov.application_showcase.presentation.navigation.NavigationRoutings

@Composable
fun NavigationPanel(
    navigationElements: List<NavigationElement>,
    start: NavigationRoutings,
    onNavigate:(NavigationRoutings)-> Unit
){
    var currentPosition by remember { mutableStateOf(start) }
    val selectedIndex = navigationElements.indexOfFirst { it.routing == currentPosition }

    var itemWidth by remember { mutableStateOf(0) }

    BoxWithConstraints(Modifier.navigationBarsPadding().fillMaxWidth().height(64.dp).clip(CircleShape)
        .background(MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.3f))

    ){ val itemWidth = maxWidth / navigationElements.size
        if (selectedIndex != -1 && itemWidth > 0.dp) {
            val targetOffset = selectedIndex * itemWidth.value
            val animatedOffset by animateDpAsState(
                targetValue = targetOffset.dp,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )

            Box(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .width(itemWidth)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            for (x in navigationElements){
                Box(Modifier.weight(1f).fillMaxHeight().clip(CircleShape).clickable{
                    onNavigate(x.routing)
                    currentPosition=x.routing
                }, contentAlignment = Alignment.Center){
                    Icon(painterResource(x.icon), tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(40.dp), contentDescription = x.name)
                }
            }
        }
    }
}