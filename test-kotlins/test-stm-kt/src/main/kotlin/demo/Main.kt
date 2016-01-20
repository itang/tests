package demo

import scala.concurrent.stm.japi.STM.*

fun main(args: Array<String>) {
    val counter = newRef(10)

    try {
        atomic {
            increment(counter, 1)
            println("counter is ${counter.get()}")

            throw Exception("roll back!!")
        }
    } catch(e: Exception) {
        println("counter is ${counter.get()}")
    }

    assert(counter.get() == 10)
}
