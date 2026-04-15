package ru.kulishov.application_showcase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform