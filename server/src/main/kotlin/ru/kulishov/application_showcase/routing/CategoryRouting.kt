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
import ru.kulishov.application_showcase.database.category.Category
import ru.kulishov.application_showcase.database.category.CategoryDao
import java.net.URLDecoder

fun Application.configureCategoryRouting(
    categoryDao: CategoryDao
){
    routing {
        post("/addCategory/{name}/{priority}"){
            val encodedName = call.parameters["name"] ?: ""
            val name = URLDecoder.decode(encodedName, "UTF-8")
            val priority = call.parameters["priority"]?.toIntOrNull()
            if(name!=null&&priority!=null){
                call.receiveMultipart().forEachPart { partData ->
                    when(partData){
                        is PartData.FileItem -> {
                            val bytes = partData.provider().readRemaining().readByteArray()
                            categoryDao.addCategory(Category(-1,name, priority==1,bytes))
                            call.respond(HttpStatusCode.OK)
                        }
                        else -> partData.dispose
                    }
                }
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/updateCategory"){
            try {
                val req = call.receive<Category>()
                categoryDao.updateCategory(req)
                call.respond(HttpStatusCode.OK)
            }catch (e: Exception){
                println(e)
            }
        }
        post("/deleteCategory/{id}"){
            try {
                val categoryId = call.parameters["id"]?.toIntOrNull()
                if(categoryId==null){
                    call.respond(HttpStatusCode.BadRequest)
                }else{
                    categoryDao.deleteCategory(categoryId)
                    call.respond(HttpStatusCode.OK)
                }
            }catch (e: Exception){
                println(e)
            }
        }

        get("/getPriorityCategory"){
            val req = categoryDao.getPriorityCategory()
            call.respond(req)
        }
        get("/getCategory"){
            val req = categoryDao.getCategoryList()
            call.respond(req)
        }

    }
}