package demo

import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val mbs = args.firstOrNull()?.toIntOrNull() ?: 1
    println("mbs:$mbs")
    thread {
        for (i in 1..1000) {
            println(i)
            Thread.sleep(1000)
        }
    }
    Thread.sleep(3 * 1000)
    println("try make ByteArray(1024 * 1024 * $mbs)...")
    val bytes = ByteArray(1024 * 1024 * mbs)
}
