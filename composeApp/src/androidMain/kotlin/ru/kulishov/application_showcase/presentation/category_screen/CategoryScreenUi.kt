package ru.kulishov.application_showcase.presentation.category_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.arrow_back
import application_showcase.composeapp.generated.resources.avatar
import org.jetbrains.compose.resources.painterResource
import ru.kulishov.application_showcase.presentation.uikit.AppCard
import ru.kulishov.application_showcase.presentation.uikit.CategoryCard
import ru.kulishov.application_showcase.presentation.uikit.ErrorScreen
import ru.kulishov.application_showcase.presentation.uikit.LoadingScreen
import ru.kulishov.application_showcase.presentation.uikit.SearchBox

@Composable
fun CategoryScreenUI(
    viewModel: CategoryScreenViewModel = hiltViewModel(),
    padding: PaddingValues
) {
    var categories = viewModel.category.collectAsState()
    var apps = viewModel.apps.collectAsState()
    var uiState = viewModel.uiState.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var searchApp = viewModel.search_apps.collectAsState()
    var currentCategory by remember { mutableStateOf(-1) }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        viewModel.getCategories()
        viewModel.getCategoryApp(currentCategory)
        isRefreshing = false
    }


    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        }
    ) {
        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            if (uiState.value != CategoryScreenViewModel.CategoryScreenUiState.LoadingApps && uiState.value != CategoryScreenViewModel.CategoryScreenUiState.Apps) {
                Box {
                    Box(Modifier
                        .padding(end = 66.dp)
                        .fillMaxWidth()) {
                        SearchBox(searchText) {
                            searchText = it
                            viewModel.searchApps(searchText)
                        }
                    }
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Box(Modifier
                            .size(56.dp)
                            .clip(CircleShape)) {
                            Image(
                                painterResource(Res.drawable.avatar),
                                "profile",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                if (searchText != "") {
                    LazyColumn {
                        items(searchApp.value) { app ->
                            val fIcon = categories.value.find { it.id == app.category }?.icon
                            AppCard(app, fIcon)
                        }
                    }
                } else {
                    when (uiState.value) {
                        CategoryScreenViewModel.CategoryScreenUiState.Apps -> {}
                        is CategoryScreenViewModel.CategoryScreenUiState.Error -> {
                            ErrorScreen {
                                isRefreshing = true
                            }
                        }

                        CategoryScreenViewModel.CategoryScreenUiState.Loading -> {
                            LoadingScreen()
                        }

                        CategoryScreenViewModel.CategoryScreenUiState.LoadingApps -> TODO()
                        CategoryScreenViewModel.CategoryScreenUiState.Success -> {
                            LazyColumn {
                                items(categories.value) {
                                    Box(Modifier.clickable {
                                        currentCategory = it.id
                                        viewModel.openCategory(it.id)
                                    }) {
                                        CategoryCard(it)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Box(Modifier.fillMaxWidth().height(30.dp), contentAlignment = Alignment.CenterStart) {
                    Icon(painterResource(Res.drawable.arrow_back), "back", tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp).clickable{viewModel.openCategoryList()})
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        val catFind = categories.value.find { it.id==currentCategory }
                        if(catFind!=null){
                            Text(catFind.name, style = MaterialTheme.typography.headlineLarge)
                        }

                    }
                }
                when(uiState.value) {
                    CategoryScreenViewModel.CategoryScreenUiState.Apps -> {
                        LazyColumn {
                            items(apps.value){
                                Box(){
                                    AppCard(it)
                                }
                            }
                        }
                    }
                    is CategoryScreenViewModel.CategoryScreenUiState.Error -> {
                        ErrorScreen { isRefreshing=true }
                    }
                    CategoryScreenViewModel.CategoryScreenUiState.Loading -> TODO()
                    CategoryScreenViewModel.CategoryScreenUiState.LoadingApps -> {
                        LoadingScreen()
                    }
                    CategoryScreenViewModel.CategoryScreenUiState.Success -> TODO()
                }
            }
        }
    }
}