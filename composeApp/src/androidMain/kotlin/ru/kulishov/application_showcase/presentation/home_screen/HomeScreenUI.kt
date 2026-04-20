package ru.kulishov.application_showcase.presentation.home_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.actual
import application_showcase.composeapp.generated.resources.avatar
import application_showcase.composeapp.generated.resources.category
import application_showcase.composeapp.generated.resources.popular
import application_showcase.composeapp.generated.resources.star_filled
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.kulishov.application_showcase.SelectedFile
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.Category
import ru.kulishov.application_showcase.domain.model.Photo
import ru.kulishov.application_showcase.domain.model.StoryWithPhoto
import ru.kulishov.application_showcase.presentation.navigation.NavigationRoutings
import ru.kulishov.application_showcase.presentation.uikit.AddCard
import ru.kulishov.application_showcase.presentation.uikit.AppCard
import ru.kulishov.application_showcase.presentation.uikit.CategoryButton
import ru.kulishov.application_showcase.presentation.uikit.LoadingElement
import ru.kulishov.application_showcase.presentation.uikit.LoadingScreen
import ru.kulishov.application_showcase.presentation.uikit.PhotoScreenUI
import ru.kulishov.application_showcase.presentation.uikit.SearchBox
import ru.kulishov.application_showcase.presentation.uikit.StoriesCard
import ru.kulishov.application_showcase.toImageBitmap

@Composable
fun HomeScreenUI(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    padding: PaddingValues,
    openApp:(AppMetadataWithLogo)->Unit,
    hideNavigation:(Boolean)->Unit
){
    val uiState = viewModel.uiState.collectAsState()
    var categories = viewModel.category.collectAsState()
    var apps = viewModel.apps.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var searchApp =viewModel.search_apps.collectAsState()
    var stories by remember { mutableStateOf(emptyList<StoryWithPhoto>()) }
    var add by remember { mutableStateOf(emptyList<StoryWithPhoto>()) }
    var currentCategory by remember { mutableStateOf(-1) }

    var isRefreshing by remember { mutableStateOf(false) }

    var currentStorie by remember { mutableStateOf(0) }

    LaunchedEffect(isRefreshing) {
        stories=viewModel.getStories()
        add = viewModel.getAdds()
        viewModel.getCategories()
        viewModel.getCategoryApp(currentCategory)
        isRefreshing=false
    }



    if(uiState.value== HomeScreenViewModel.HomeScreenUiState.Story){
        PhotoScreenUI(emptyList<Photo>(),currentStorie,
            stories,{
                hideNavigation(false)
                viewModel.closeStory()
            },padding
            )
    }else{
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing=true
            },
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = Modifier.padding(padding),
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {
                Box{
                    Box(Modifier.padding(end = 66.dp).fillMaxWidth()){
                        SearchBox(searchText) {
                            searchText=it
                            viewModel.searchApps(searchText)
                        }
                    }
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                        Box(Modifier.size(56.dp).clip(CircleShape)){
                            Image(painterResource(Res.drawable.avatar), "profile", modifier = Modifier.fillMaxSize())
                        }
                    }
                }
                if(searchText!=""){
                    LazyColumn {
                        items(searchApp.value){ app->
                            val fIcon = categories.value.find { it.id== app.category}?.icon
                            Box(Modifier.clickable{
                                openApp(app)
                            }){
                                AppCard(app,fIcon)
                            }

                        }
                    }
                }else{
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        item{
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                            ) {
                                Text(
                                    stringResource(Res.string.actual),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    itemsIndexed(stories) {index,item->
                                        Box(
                                            Modifier.clickable{
                                                hideNavigation(true)
                                                currentStorie=index
                                                viewModel.openStory()
                                            }
                                        ){
                                            StoriesCard(item)
                                        }

                                    }
                                    items(4){
                                        if(stories.isEmpty()){
                                            Box(Modifier.width(100.dp).height(145.dp).clip(RoundedCornerShape(4))){
                                                LoadingElement()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            Box(){
                                if(add.isNotEmpty()){
                                    AddCard(add[0])
                                }else{
                                    Box(Modifier.fillMaxWidth()
                                        .height(200.dp).clip(RoundedCornerShape(5))){
                                        LoadingElement()
                                    }
                                }
                            }

                        }
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
//                            item {
//                                Box(
//                                    Modifier
//                                        .clip(CircleShape)
//                                        .background(if (currentCategory==-2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer)
//                                        .clickable{
//                                            onCategoryList()
//                                        },
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Row(
//                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
//                                    ) {
//                                        Icon(painterResource(Res.drawable.category), "category", tint = MaterialTheme.colorScheme.primary)
//                                        Text(stringResource(Res.string.category), style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp, color =
//                                            MaterialTheme.colorScheme.onSurface))
//                                    }
//                                }
//
//                            }
                                item {
                                    Box(
                                        Modifier
                                            .clip(CircleShape)
                                            .background(if (currentCategory==-1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer)
                                            .clickable{
                                                currentCategory=-1
                                                viewModel.getCategoryApp(-1)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Box(Modifier.size(20.dp), contentAlignment = Alignment.Center){
                                                Icon(painterResource(Res.drawable.star_filled), "category", tint = Color.Yellow, modifier = Modifier.size(16.dp))

                                            }
                                            Text(stringResource(Res.string.popular), style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp, color =
                                                if(currentCategory==-1) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface))
                                        }
                                    }

                                }
                                items(categories.value){
                                    if(it.priority){
                                        Box(Modifier.clip(CircleShape).clickable{
                                            currentCategory=it.id
                                            viewModel.getCategoryApp(it.id)
                                        }){
                                            CategoryButton(it,it.id==currentCategory)
                                        }
                                    }


                                }
                            }
                        }
                        item{
                            if(apps.value.isEmpty()){
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    LoadingScreen()
                                }
                            }else {
                                Column {
                                    for (app in apps.value) {
                                        Box(Modifier.clickable {
                                            openApp(app)
                                        }) {
                                            AppCard(app)
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }
        }


    }



}
