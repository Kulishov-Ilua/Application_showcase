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
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select
import ru.kulishov.application_showcase.database.add.Add
import ru.kulishov.application_showcase.database.add.AddDao
import ru.kulishov.application_showcase.database.app.Logo
import ru.kulishov.application_showcase.database.category.CategoryDao
import ru.kulishov.application_showcase.database.photo.Photo
import ru.kulishov.application_showcase.database.photo.PhotoDao
import ru.kulishov.application_showcase.database.photo.Photos
import ru.kulishov.application_showcase.database.storie.Story
import ru.kulishov.application_showcase.database.storie.StoryDao
import kotlin.text.toIntOrNull

fun Application.configureMediaRouting(
    photoDao: PhotoDao,
    storyDao: StoryDao,
    addDao: AddDao
){
    routing {
        post("/addPhoto/{appId}/{isView}"){
            val appId = call.parameters["appId"]?.toIntOrNull()
            val isView = call.parameters["isView"]?.toIntOrNull()
            if(appId!=null&&isView!=null){
                call.receiveMultipart().forEachPart { partData ->
                    when(partData){
                        is PartData.FileItem -> {
                            val bytes = partData.provider().readRemaining().readByteArray()
                           photoDao.addPhoto(Photo(
                               id = -1,
                               appId = appId,
                               type = "image/png",
                               data = bytes,
                               size = bytes.size.toLong(),
                               isView = isView==1
                           ))
                            call.respond(HttpStatusCode.OK)
                        }
                        else -> partData.dispose
                    }
                }
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/getPhotoListByApp/{id}"){
            val appId = call.parameters["id"]?.toIntOrNull()
            if(appId!=null){
                call.respond(photoDao.getPhotoByApp(appId))
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/getPhotoById/{id}"){
            val id = call.parameters["id"]?.toLongOrNull()
            if(id!=null){
                val req = photoDao.getPhotoById(id)
                if(req!=null){
                    call.respond(req)
                } else call.respond(HttpStatusCode.NotFound)
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/addStory") {
            val body = call.receive<Story>()
            storyDao.addStory(body)
            call.respond(HttpStatusCode.OK)
        }


        get("/getLastStory") {
            call.respond(storyDao.getLastStories())
        }

        post("/addPoster") {
            val body = call.receive<Add>()
            addDao.addAdd(body)
            call.respond(HttpStatusCode.OK)
        }

        get("/getLastAdd") {
            call.respond(addDao.getLastAdds())
        }
    }
}