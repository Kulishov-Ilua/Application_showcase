package ru.kulishov.application_showcase.presentation.uikit

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.exit
import kotlinx.datetime.format.Padding
import org.jetbrains.compose.resources.painterResource
import ru.kulishov.application_showcase.SelectedFile
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.model.Story
import ru.kulishov.application_showcase.domain.model.StoryWithPhoto
import ru.kulishov.application_showcase.toImageBitmap
import kotlin.math.abs

@Composable
fun PhotoScreenUI(
    photos:List<Photo>,
    currentPhoto:Int,
    stories:List<StoryWithPhoto>? = null,
    onClose:()->Unit,
    padding: PaddingValues
){
    var newCurrentPhoto by remember { mutableStateOf(currentPhoto) }
    if(stories!=null){
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")

        val animatedValue by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000),
                repeatMode = RepeatMode.Restart
            ),

        )
        LaunchedEffect(animatedValue) {
            if (animatedValue>0.999993f) {
                if(newCurrentPhoto<(stories.size-1)) newCurrentPhoto++
                else onClose()
            }
        }
        Box(Modifier.fillMaxSize().background(Color.Black)
            , contentAlignment = Alignment.Center){
            if(newCurrentPhoto< stories.size){

                if(stories[newCurrentPhoto].photo!=null){
                    val bitmap =
                        SelectedFile("", "", stories[newCurrentPhoto].photo!!).toImageBitmap()
                    if (bitmap != null) {
                        Image(
                            bitmap,
                            contentDescription = "photo",
                            modifier = Modifier.fillMaxSize(),
                            filterQuality = FilterQuality.High
                        )
                    }
                }



            }

        }
        Column(Modifier.padding(padding).padding(top=5.dp, end = 15.dp, start = 15.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)){

            Box(Modifier.fillMaxWidth(animatedValue).height(5.dp).clip(CircleShape).background(
                MaterialTheme.colorScheme.onSurface))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(stories[currentPhoto].appMetadata!=null){
                    if(stories[currentPhoto].appMetadata!!.logo!=null){
                        val bitmap = SelectedFile("", "", stories[newCurrentPhoto].appMetadata!!.logo!!).toImageBitmap()
                        if (bitmap != null) {
                            Image(
                                bitmap,
                                contentDescription = "logo",
                                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10))
                            )
                        }
                        Text(stories[newCurrentPhoto].appMetadata!!.name, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.surface))
                    }

                }
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Icon(painterResource(Res.drawable.exit), "close", tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable{
                            onClose()
                        })
                }
            }


        }

    }else{
        Box(Modifier.fillMaxSize().background(Color.Black)
            .pointerInput(Unit){
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        if (abs(dragAmount) > 100f) {
                            if (dragAmount > 0) {
                                if(newCurrentPhoto>0) newCurrentPhoto--
                                else newCurrentPhoto=photos.size-1
                            } else {
                                if(newCurrentPhoto<(photos.size-1)) newCurrentPhoto++
                                else newCurrentPhoto=0
                            }
                        }
                    }
                )
            }
            , contentAlignment = Alignment.Center){
            if(newCurrentPhoto< photos.size){
                val bitmap =
                    SelectedFile("", "", photos[newCurrentPhoto].data).toImageBitmap()
                if (bitmap != null) {
                    Image(
                        bitmap,
                        contentDescription = "photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
        Box(Modifier.padding(padding).padding(top=5.dp, end = 15.dp).fillMaxWidth(), contentAlignment = Alignment.CenterEnd){

            Icon(painterResource(Res.drawable.exit), "close", tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable{
                    onClose()
                })
        }
        Box(Modifier.padding(padding).padding(bottom = 25.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                for (i in 0 until photos.size) {
                    val isSelected = i == newCurrentPhoto
                    val targetWidth = if (isSelected) 20.dp else 7.dp

                    val animatedWidth by animateDpAsState(
                        targetValue = targetWidth,
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    )

                    Box(
                        Modifier
                            .height(7.dp)
                            .width(animatedWidth)
                            .clip(
                                CircleShape
                            )
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                    )
                }
            }
        }
        Box(Modifier.padding(bottom = 300.dp, top = 100.dp).fillMaxSize()) {
            Row(Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxHeight().weight(1f).clickable {
                    if (newCurrentPhoto > 0) newCurrentPhoto--
                    else newCurrentPhoto = photos.size - 1
                })
                Box(Modifier.fillMaxHeight().weight(1f).clickable {
                    if (newCurrentPhoto < (photos.size - 1)) newCurrentPhoto++
                    else newCurrentPhoto = 0
                })
            }
        }
    }

}