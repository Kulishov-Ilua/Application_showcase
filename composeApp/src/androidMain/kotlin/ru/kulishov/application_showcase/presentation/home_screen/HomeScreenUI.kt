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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import ru.kulishov.application_showcase.domain.model.StoryWithPhoto
import ru.kulishov.application_showcase.presentation.uikit.AddCard
import ru.kulishov.application_showcase.presentation.uikit.AppCard
import ru.kulishov.application_showcase.presentation.uikit.CategoryButton
import ru.kulishov.application_showcase.presentation.uikit.SearchBox
import ru.kulishov.application_showcase.presentation.uikit.StoriesCard
import ru.kulishov.application_showcase.toImageBitmap

@Composable
fun HomeScreenUI(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    padding: PaddingValues
){
    var categories = viewModel.category.collectAsState()
    var apps = viewModel.apps.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var searchApp =viewModel.search_apps.collectAsState()
    var stories by remember { mutableStateOf(emptyList<StoryWithPhoto>()) }
    var add by remember { mutableStateOf(emptyList<StoryWithPhoto>()) }
    var currentCategory by remember { mutableStateOf(-1) }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        Log.d(null, "update")
        stories=viewModel.getStories()
        add = viewModel.getAdds()
        viewModel.getCategories()
        viewModel.getCategoryApp(currentCategory)
        isRefreshing=false
    }




    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing=true
        }
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
                        AppCard(app,fIcon)
                    }
                }
            }else{
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    item{
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier.height(176.dp)
                        ) {
                            Text(
                                stringResource(Res.string.actual),
                                style = MaterialTheme.typography.titleLarge
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(stories) {
                                    StoriesCard(it)
                                }
                            }
                        }
                    }
                    item {
                        Box(Modifier.height(200.dp)){
                            if(add.size>0){
                                AddCard(add[0])
                            }
                        }

                    }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            item {
                                Box(
                                    Modifier
                                        .clip(CircleShape)
                                        .background(if (currentCategory==-2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer)
                                        .clickable{

                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(painterResource(Res.drawable.category), "category", tint = MaterialTheme.colorScheme.primary)
                                        Text(stringResource(Res.string.category), style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp, color =
                                            MaterialTheme.colorScheme.surface))
                                    }
                                }

                            }
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
                                            MaterialTheme.colorScheme.surface))
                                    }
                                }

                            }
                            items(categories.value){
                                Box(Modifier.clip(CircleShape).clickable{
                                    currentCategory=it.id
                                    viewModel.getCategoryApp(it.id)
                                }){
                                    CategoryButton(it,it.id==currentCategory)
                                }

                            }
                        }
                    }
                    item{
                        Column {
                            for(app in apps.value){
                                AppCard(app)
                            }
                        }


                    }
                }
            }

        }
    }
    



}
