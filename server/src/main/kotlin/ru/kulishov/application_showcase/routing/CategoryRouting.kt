package ru.kulishov.application_showcase.routing

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import ru.kulishov.application_showcase.database.category.Category
import ru.kulishov.application_showcase.database.category.CategoryDao

fun Application.configureCategoryRouting(
    categoryDao: CategoryDao
){
    routing {
        post("/addCategory"){
            try {
                val req = call.receive<Category>()
                categoryDao.addCategory(req)
                call.respond(HttpStatusCode.OK)
            }catch (e: Exception){
                println(e)
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