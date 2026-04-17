package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.install
import application_showcase.composeapp.generated.resources.star_filled
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kulishov.application_showcase.SelectedFile
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.toImageBitmap

@Composable
fun AppCard(
    data: AppMetadataWithLogo,
    icon: ByteArray? = null
) {
    val infiniteTransition = rememberInfiniteTransition()
    val targetColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.3f),
        targetValue = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5))
                    .background(if (data.logo == null) targetColor else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                if (data.logo != null) {
                    val bitmap = SelectedFile("", "", data.logo!!).toImageBitmap()
                    if (bitmap != null) {
                        Image(
                            bitmap,
                            contentDescription = "Logo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        data.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp)
                    )
                    Box(Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                        if (icon != null) {
                            val bitmap = SelectedFile("", "", icon).toImageBitmap()
                            if (bitmap != null) {
                                Image(
                                    bitmap,
                                    contentDescription = "category",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                    }
                }
                Text(
                    data.short_description,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        String.format("%.1f", data.grade),
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp)
                    )
                    Box(Modifier.size(12.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            painterResource(Res.drawable.star_filled),
                            "grade",
                            tint = Color.Yellow
                        )
                    }
                }


            }

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.install),
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp,color = MaterialTheme.colorScheme.surface)
                    )
                }
            }

        }
    }
}