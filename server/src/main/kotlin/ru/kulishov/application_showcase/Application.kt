package ru.kulishov.application_showcase

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import ru.kulishov.application_showcase.database.DBFactory
import ru.kulishov.application_showcase.database.add.AddDao
import ru.kulishov.application_showcase.database.app.AppDao
import ru.kulishov.application_showcase.database.category.CategoryDao
import ru.kulishov.application_showcase.database.photo.PhotoDao
import ru.kulishov.application_showcase.database.storie.StoryDao
import ru.kulishov.application_showcase.routing.configureAppRouting
import ru.kulishov.application_showcase.routing.configureCategoryRouting
import ru.kulishov.application_showcase.routing.configureMediaRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
//    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
}

fun Application.module() {
    install(CORS){
        anyHost()
    }
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    DBFactory.init(environment)
    val categoryDao= CategoryDao()
    val appDao = AppDao()
    val photoDao= PhotoDao()
    val storyDao = StoryDao()
    val addDao = AddDao()

    configureCategoryRouting(categoryDao)
    configureAppRouting(appDao)
    configureMediaRouting(photoDao,storyDao,addDao)

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}