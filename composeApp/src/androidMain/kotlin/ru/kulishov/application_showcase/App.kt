package ru.kulishov.application_showcase

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.categorynav
import application_showcase.composeapp.generated.resources.home
import application_showcase.composeapp.generated.resources.profile
import application_showcase.composeapp.generated.resources.shop
import ru.kulishov.application_showcase.presentation.AppTheme
import ru.kulishov.application_showcase.presentation.app_screen.AppScreenUI
import ru.kulishov.application_showcase.presentation.category_screen.CategoryScreenUI
import ru.kulishov.application_showcase.presentation.home_screen.HomeScreenUI
import ru.kulishov.application_showcase.presentation.navigation.NavigationElement
import ru.kulishov.application_showcase.presentation.navigation.NavigationRoutings
import ru.kulishov.application_showcase.presentation.preview_screen.PreviewScreenUI
import ru.kulishov.application_showcase.presentation.uikit.NavigationPanel


@Composable
@Preview
fun App() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    var isFirst by remember { mutableStateOf(prefs.getBoolean("isFirstRun", true)) }
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(true) }

    AppTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    NavigationPanel(
                        navigationElements = listOf(
                            NavigationElement(
                                NavigationRoutings.HomeScreen,
                                Res.drawable.home,
                                "Главная"
                            ),
                            NavigationElement(
                                NavigationRoutings.CategoryScreen,
                                Res.drawable.categorynav,
                                "Категории"
                            ),
                            NavigationElement(
                                NavigationRoutings.ShopScreen,
                                Res.drawable.shop,
                                "Магазин"
                            ),
                            NavigationElement(
                                NavigationRoutings.ProfileScreen,
                                Res.drawable.profile,
                                "Профиль"
                            ),
                        ),
                        start = NavigationRoutings.HomeScreen,
                        onNavigate = { routing ->
                            navController.navigate(routing) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            },
            content = { padding ->
                NavHost(
                    navController = navController,
                    startDestination = if (isFirst) NavigationRoutings.PreviewScreen else NavigationRoutings.HomeScreen,
                ) {
                    composable<NavigationRoutings.PreviewScreen> {
                        LaunchedEffect(1) {
                            prefs.edit().putBoolean("isFirstRun", false).apply()
                            showBottomBar = false
                        }
                        Box(
                            Modifier
                                .padding(padding)
                                .fillMaxSize()
                        ) {
                            1
                            PreviewScreenUI {
                                navController.navigate(NavigationRoutings.HomeScreen)
                            }
                        }
                        DisposableEffect(Unit) {
                            onDispose {
                                showBottomBar = true
                            }
                        }

                    }
                    composable<NavigationRoutings.HomeScreen> {

                        HomeScreenUI(padding = padding, openApp = {
                            navController.navigate(
                                NavigationRoutings.AppScreen(it.id)
                            ) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }, hideNavigation = { state ->
                            if (state) {
                                showBottomBar = false
                            } else {
                                showBottomBar = true
                            }
                        }
                        )
                    }
                    composable<NavigationRoutings.CategoryScreen> {
                        CategoryScreenUI(padding = padding, openApp = {
                            navController.navigate(
                                NavigationRoutings.AppScreen(it.id)
                            ) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })

                    }
                    composable<NavigationRoutings.ShopScreen> {


                    }
                    composable<NavigationRoutings.ProfileScreen> {


                    }
                    composable<NavigationRoutings.AppScreen> { backStackEntry ->
                        val id = backStackEntry.toRoute<NavigationRoutings.AppScreen>().id

                        //val category = backStackEntry.toRoute<NavigationRoutings.AppScreen>().category
                        AppScreenUI(
                            startApp = id,
                            padding = padding,
                            onBack = { navController.popBackStack() },
                            hideNavigation = { state ->
                                if (state) {
                                    showBottomBar = false
                                } else {
                                    showBottomBar = true
                                }
                            })
                    }
                }
            }

        )

    }
}

@Composable
fun SelectedFile.toImageBitmap(): ImageBitmap? {
    return try {
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}

data class SelectedFile(
    val name: String,
    val type: String,
    val bytes: ByteArray,
)
