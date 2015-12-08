package demo

import nl.komponents.kovenant.async
import nl.komponents.kovenant.combine.and
import nl.komponents.kovenant.then

fun main(args: Array<String>) {
  async { "world" } then { it.toUpperCase() } and async { "Hello" } success {
    println("${it.second} ${it.first}!")
  }
}
