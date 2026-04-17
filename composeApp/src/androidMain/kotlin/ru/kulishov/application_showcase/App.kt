package ru.kulishov.application_showcase

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource

import application_showcase.composeapp.generated.resources.Res
import application_showcase.composeapp.generated.resources.compose_multiplatform
import ru.kulishov.application_showcase.data.remote.repository.AppMetadataRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.CategoryRepositoryImpl
import ru.kulishov.application_showcase.data.remote.repository.LogoRepositoryImpl
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetCategoryAppUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.GetPopularAppUseCase
import ru.kulishov.application_showcase.domain.usecase.app_metadata.SearchAppUseCase
import ru.kulishov.application_showcase.domain.usecase.category.GetCategoryUseCase
import ru.kulishov.application_showcase.domain.usecase.logo.GetLogoUseCase
import ru.kulishov.application_showcase.presentation.AppTheme
import ru.kulishov.application_showcase.presentation.home_screen.HomeScreenUI
import ru.kulishov.application_showcase.presentation.home_screen.HomeScreenViewModel
import ru.kulishov.application_showcase.presentation.navigation.HomeScreen
import ru.kulishov.application_showcase.presentation.navigation.PreviewScreen
import ru.kulishov.application_showcase.presentation.preview_screen.PreviewScreenUI
import ru.kulishov.application_showcase.presentation.uikit.SearchBox


@Composable
@Preview
fun App() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    var isFirst by remember { mutableStateOf(prefs.getBoolean("isFirstRun", true)) }
    val navController = rememberNavController()
    AppTheme {
        Scaffold (
            containerColor = MaterialTheme.colorScheme.surface,
            content = {
                padding ->
                        NavHost(navController = navController, startDestination = if(isFirst) PreviewScreen else HomeScreen){
                            composable<PreviewScreen> {
                                LaunchedEffect(1) {
                                    prefs.edit().putBoolean("isFirstRun", false).apply()
                                }
                                Box(Modifier.padding(padding).fillMaxSize()){1
                                    PreviewScreenUI {
                                        navController.navigate(HomeScreen)
                                    }
                                }

                            }
                            composable<HomeScreen>{

                                HomeScreenUI(padding = padding)
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
