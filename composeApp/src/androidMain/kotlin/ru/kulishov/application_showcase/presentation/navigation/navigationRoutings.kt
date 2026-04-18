package ru.kulishov.application_showcase.presentation.navigation

import kotlinx.serialization.Serializable

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
}

