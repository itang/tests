package demo

import java.io.File

fun main(args: Array<String>) {
    println("Hello, Kotlin")

    File("hello.txt").writeText("hello")
}
