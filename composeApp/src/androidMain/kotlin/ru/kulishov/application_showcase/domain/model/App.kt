package ru.kulishov.application_showcase.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class App(
    var id:Int,
    var name:String,
    var author:String,
    var category:Int,
    var grade:Float,
    var gradesCount:Long,
    var age:Int,
    var description:String,
    var short_description:String
)
