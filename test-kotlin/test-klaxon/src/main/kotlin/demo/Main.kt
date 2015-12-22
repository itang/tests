package demo

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.string

fun parse(name: String): Any {
    val cls = Parser::class.java
    val inputStream = cls.getResourceAsStream(name)!!
    return Parser().parse(inputStream)!!
}

fun main(args: Array<String>) {
    val obj = parse("/data.json") as JsonObject
    val firstName = obj.string("firstName")
    val lastName = obj.string("lastName")
    println("Name: ${firstName} ${lastName}")

    println(obj.toJsonString(true))
}
