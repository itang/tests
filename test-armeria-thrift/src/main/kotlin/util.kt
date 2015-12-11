package util

import java.text.SimpleDateFormat
import java.util.Date

fun <T> time(t: () -> T): T {
    val start = System.nanoTime()

    val ret = t()

    val end = System.nanoTime()

    println("Elapsed time: ${((end - start) / 1000000.0)} msecs")

    return ret
}

fun Int.loop(block: (x: Int) -> Unit): Unit {
    var i = 0
    val max = this
    while (i < max) {
        block(i)
        i += 1
    }
}

fun Date.strfmt(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return SimpleDateFormat(pattern).format(this)
}
