package ru.kulishov.application_showcase.routing

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import ru.kulishov.application_showcase.database.app.App
import ru.kulishov.application_showcase.database.app.AppDao
import ru.kulishov.application_showcase.database.app.Logo
import ru.kulishov.application_showcase.database.category.Category
import ru.kulishov.application_showcase.database.category.CategoryDao
import java.net.URLDecoder
import kotlin.text.toIntOrNull

fun Application.configureAppRouting(
    appDao: AppDao
){
    routing {
        post("/addApp") {
            val body = call.receive<App>()
            appDao.addApp(body)
            call.respond(HttpStatusCode.OK)
        }

        post("/addAppIcon/{appId}"){
            val appId = call.parameters["appId"]?.toIntOrNull()
            if(appId!=null){
                call.receiveMultipart().forEachPart { partData ->
                    when(partData){
                        is PartData.FileItem -> {
                            val bytes = partData.provider().readRemaining().readByteArray()
                            appDao.addAppIcon(Logo(-1,appId,bytes))
                            call.respond(HttpStatusCode.OK)
                        }
                        else -> partData.dispose
                    }
                }
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/getPopularApp/{limit}"){
            val limit = call.parameters["limit"]?.toIntOrNull()
            if(limit!=null){
                call.respond(appDao.getPopularApp(limit))
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }

        }

        get("/getCategoryApp/{id}/{limit}"){
            val limit = call.parameters["limit"]?.toIntOrNull()
            val id = call.parameters["id"]?.toIntOrNull()
            if(limit!=null&&id!=null){
                call.respond(appDao.getAppByCategory(id,limit))
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/getApp/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if(id!=null){
                val app = appDao.getApp(id)
                if(app!=null){
                    call.respond(app)
                }else call.respond(HttpStatusCode.NotFound)
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/getAppLogo/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if(id!=null){
                val logo = appDao.getLogo(id)
                if(logo!=null){
                    call.respond(logo)
                }else call.respond(HttpStatusCode.NotFound)
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

    }
}