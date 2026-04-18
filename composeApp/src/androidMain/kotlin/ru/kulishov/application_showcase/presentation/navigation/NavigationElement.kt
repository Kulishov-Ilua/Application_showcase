package ru.kulishov.application_showcase.presentation.navigation

import org.jetbrains.compose.resources.DrawableResource

data class NavigationElement(
    val routing: NavigationRoutings,
    val icon: DrawableResource,
    val name: String
)
