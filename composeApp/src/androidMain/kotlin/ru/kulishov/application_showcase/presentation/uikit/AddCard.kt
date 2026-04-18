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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun AddCard(add: StoryWithPhoto){
    Box(Modifier.fillMaxWidth()
        .height(200.dp).clip(RoundedCornerShape(5)),
        contentAlignment = Alignment.BottomStart){
        if(add.photo!=null){
            val bitmap = SelectedFile("", "", add.photo).toImageBitmap()
            if (bitmap != null) {
                Image(
                    bitmap,
                    contentDescription = "category",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }else{
                LoadingElement()
            }
        }else{
            LoadingElement()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
        ) {
            if(add.appMetadata!=null){
                if(add.appMetadata.logo!=null){
                    val bitmap = SelectedFile("", "", add.appMetadata.logo!!).toImageBitmap()
                    if (bitmap != null) {
                        Image(
                            bitmap,
                            contentDescription = "logo",
                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10))
                        )
                    }
                    Text(add.appMetadata.name, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.surface))
                }

            }
        }

    }
}