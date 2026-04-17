package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.star_filled
import org.jetbrains.compose.resources.painterResource
import ru.kulishov.application_showcase.SelectedFile
import ru.kulishov.application_showcase.domain.model.StoryWithPhoto
import ru.kulishov.application_showcase.toImageBitmap

@Composable
fun StoriesCard(story: StoryWithPhoto){
    Box(Modifier.width(100.dp).height(145.dp).clip(RoundedCornerShape(4)), contentAlignment = Alignment.BottomStart){
        if(story.photo!=null){
            val bitmap = SelectedFile("", "", story.photo).toImageBitmap()
            if (bitmap != null) {
                Image(
                    bitmap,
                    contentDescription = "category",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }else{
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
        }else{
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
        ) {
            if(story.appMetadata!=null){
                Text(String.format("%.1f", story.appMetadata.grade), style = MaterialTheme.typography.titleLarge.copy(fontSize = 9.sp, color = Color.Yellow))
                Icon(painterResource(Res.drawable.star_filled), "grade", tint = Color.Yellow, modifier = Modifier.size(10.dp))
                Text(story.appMetadata.name, style = MaterialTheme.typography.titleLarge.copy(fontSize = 9.sp, color = MaterialTheme.colorScheme.surface))
            }

        }
    }
}