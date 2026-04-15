package ru.kulishov.application_showcase.database.category

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table

object Categories: Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 127)
    val icon =  binary("icon")
    val priority = bool("priority")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Category(
    val id:Int,
    val name:String,
    val priority: Boolean,
    val icon: ByteArray
)