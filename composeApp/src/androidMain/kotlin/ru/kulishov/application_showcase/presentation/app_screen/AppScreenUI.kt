package ru.kulishov.application_showcase.presentation.app_screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.album
import application_showcase.composeapp.generated.resources.arrow_back
import application_showcase.composeapp.generated.resources.description
import application_showcase.composeapp.generated.resources.install
import application_showcase.composeapp.generated.resources.profile
import application_showcase.composeapp.generated.resources.star_filled
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kulishov.application_showcase.SelectedFile
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.AppWithLogo
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.presentation.uikit.LoadingElement
import ru.kulishov.application_showcase.presentation.uikit.PhotoScreenUI
import ru.kulishov.application_showcase.toImageBitmap

@Composable
fun AppScreenUI(
    viewModel: AppScreenViewModel = hiltViewModel(),
    startApp: Int,
    onBack: () -> Unit,
    padding: PaddingValues,
    hideNavigation:(Boolean)->Unit
) {
    var uiState = viewModel.uiState.collectAsState()
    var app = viewModel.app.collectAsState()
    var photo = viewModel.photo.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    var currentPhoto by remember { mutableStateOf(0) }
    LaunchedEffect(startApp) {
        viewModel.setId(startApp)
        viewModel.refreshApp()
    }

    LaunchedEffect(isRefreshing) {
        viewModel.refreshApp()
        isRefreshing = false
    }

    if(uiState.value!= AppScreenViewModel.AppScreenUiState.Photo){
        hideNavigation(false)
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(padding),
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {
                item{
                    Box() {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        ) {
                            if (uiState.value == AppScreenViewModel.AppScreenUiState.Success && photo.value.isNotEmpty()) {
                                val bitmap =
                                    SelectedFile("", "", photo.value[currentPhoto].data).toImageBitmap()
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
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(30.dp), contentAlignment = Alignment.TopCenter
                        ) {

                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                if (uiState.value == AppScreenViewModel.AppScreenUiState.Success) {
                                    LaunchedEffect(1) {
                                        while (true) {
                                            delay(5000)
                                            if (currentPhoto < (photo.value.size - 1)) currentPhoto++
                                            else currentPhoto = 0
                                        }
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        for (i in 0 until photo.value.size) {
                                            val isSelected = i == currentPhoto
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
                            }
                        }

                        Column(
                            modifier = Modifier
                                .padding(top = 178.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                Modifier
                                    .size(84.dp)
                                    .clip(RoundedCornerShape(10))
                                    .background(
                                        color = MaterialTheme.colorScheme.surface
                                    ), contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    Modifier
                                        .size(74.dp)
                                        .clip(RoundedCornerShape(6))
                                ) {
                                    if (app.value.logo != null) {
                                        val bitmap = SelectedFile("", "", app.value.logo!!).toImageBitmap()
                                        if (bitmap != null) {
                                            Image(
                                                bitmap,
                                                contentDescription = "photo",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    } else {
                                        LoadingElement()
                                    }
                                }


                            }
                            Text(app.value.app.name, style = MaterialTheme.typography.headlineLarge)
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp)
                                    .fillMaxWidth()
                                    .height(70.dp)

                            ) {
                                Box(
                                    Modifier
                                        .fillMaxHeight()
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Icon(
                                            painterResource(Res.drawable.profile),
                                            "author",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(30.dp)
                                        )
                                        Text(
                                            app.value.app.author,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                                Box(
                                    Modifier
                                        .fillMaxHeight()
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        if (app.value.category != null) {
                                            val bitmap = SelectedFile(
                                                "",
                                                "",
                                                app.value.category!!.icon
                                            ).toImageBitmap()
                                            if (bitmap != null) {
                                                Image(
                                                    bitmap,
                                                    contentDescription = "category",
                                                    modifier = Modifier.size(24.dp),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Text(
                                                    app.value.category!!.name,
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }

                                    }
                                }
                                Box(
                                    Modifier
                                        .fillMaxHeight()
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                                        ) {
                                            Text(
                                                String.format("%.1f", app.value.app.grade),
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Icon(
                                                painterResource(Res.drawable.star_filled),
                                                "grade",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.size(21.dp)
                                            )
                                        }
                                        var nText = ""
                                        if (app.value.app.gradesCount > 1000000) {
                                            nText = "${(app.value.app.gradesCount / 1000000).toInt()} млн."
                                        } else if (app.value.app.gradesCount > 1000) {
                                            nText = "${(app.value.app.gradesCount / 1000).toInt()} тыс."
                                        } else {
                                            nText = app.value.app.gradesCount.toString()
                                        }
                                        Text(nText, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                Box(
                                    Modifier
                                        .fillMaxHeight()
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {

                                        Text(
                                            "${app.value.app.age}+",
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        Text("Возраст", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                            Button(
                                onClick = {
                                    //todo
                                },
                                modifier = Modifier.padding(horizontal = 15.dp).fillMaxWidth(),
                                contentPadding = PaddingValues(vertical = 16.dp)
                            ) {
                                Text(
                                    stringResource(Res.string.install),
                                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.surface)
                                )
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            stringResource(Res.string.description),
                            style = MaterialTheme.typography.titleLarge
                        )
                        if(uiState.value== AppScreenViewModel.AppScreenUiState.Success){
                            Text(app.value.app.description, style = MaterialTheme.typography.bodyMedium)
                        }

                    }
                }
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            stringResource(Res.string.album),
                            style = MaterialTheme.typography.titleLarge
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(photo.value.size) { index ->
                                val item = photo.value[index]
                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(174.dp)
                                        .clip(RoundedCornerShape(5))
                                        .clickable {
                                            currentPhoto = index
                                            viewModel.openPhotoScreen()
                                        }
                                ) {
                                    if (uiState.value == AppScreenViewModel.AppScreenUiState.Success && photo.value.isNotEmpty()) {
                                        val bitmap = SelectedFile("", "", item.data).toImageBitmap()
                                        if (bitmap != null) {
                                            Image(
                                                bitmap = bitmap,
                                                contentDescription = "photo",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }
                            items(4){
                                if(photo.value.isEmpty()){
                                    Box(Modifier.width(100.dp).height(174.dp).clip(RoundedCornerShape(5))){
                                        LoadingElement()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Box(Modifier.padding(padding).padding(top=5.dp,start=15.dp).fillMaxWidth()){
                Icon(
                    painterResource(Res.drawable.arrow_back),
                    "back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBack() })
            }
        }
    }else{
        hideNavigation(true)
        PhotoScreenUI(photo.value,currentPhoto, onClose = {viewModel.closePhotoScreen()}, padding = padding)
    }


}