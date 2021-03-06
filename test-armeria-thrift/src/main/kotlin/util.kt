package util

import java.text.SimpleDateFormat
import java.util.*

fun <T> time(action: () -> T): Pair<T, Double> {
    val start = System.nanoTime()

    val ret = action()

    val elapsed = (System.nanoTime() - start) / 1000000.0

    println("Elapsed time: $elapsed msecs")

    return Pair(ret, elapsed)
}

fun Int.loop(consumer: (x: Int) -> Unit): Unit {
    val max = this

    var i = 0
    while (i < max) {
        consumer(i)
        i += 1
    }
}

fun Date.strfmt(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return SimpleDateFormat(pattern).format(this)
}

fun <T> T.tap(action: (T) -> Unit): T {
    action(this)

    return this
}
