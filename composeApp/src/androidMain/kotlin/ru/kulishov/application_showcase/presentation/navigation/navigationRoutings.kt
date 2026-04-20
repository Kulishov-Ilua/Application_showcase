package ru.kulishov.application_showcase.presentation.navigation

import kotlinx.serialization.Serializable
import ru.kulishov.application_showcase.domain.model.AppMetadataWithLogo
import ru.kulishov.application_showcase.domain.model.AppWithLogo
import ru.kulishov.application_showcase.domain.model.Category

sealed class NavigationRoutings{
    @Serializable
    object PreviewScreen: NavigationRoutings()

    @Serializable
    object HomeScreen: NavigationRoutings()
    @Serializable
    object CategoryScreen: NavigationRoutings()

    @Serializable
    object ShopScreen: NavigationRoutings()

    @Serializable
    object ProfileScreen: NavigationRoutings()

    @Serializable
    data class AppScreen(val id: Int): NavigationRoutings()
}

