package ru.kulishov.application_showcase.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json



object KtorClient {
    val BASE_URL = "http://10.0.2.2:3000"
    val client: HttpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 5000
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}