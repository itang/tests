package demo

fun main(args: Array<String>) {
    test1()
    test_extension()
}

// extension method
fun <T> T.tap(consumer: (x: T) -> Unit): T {
    consumer(this)

    return this
}

fun <A, B> tryIt(a: A?, consumer: (A) -> B) : B? {
    if (a == null) {
        return null
    } else {
        try {
            return consumer(a)
        } catch(e: Exception) {
            println("WARN: ${e.message}")
            return null
        }
    }
}

fun test1() {
    fun getValue(): String? = "1000d"

    fun parseInt(s: String?): Int? {
       return tryIt(s){ Integer.parseInt(it) }
    }

    fun pi(s: String): Int? = parseInt(s)

    DEBUG("Hello, Kotlin")
    DEBUG(parseInt("100"))
    DEBUG(parseInt("100x"))

    val s = getValue() as String // hacked
    DEBUG(pi(s))
}

fun test_extension() {
    val len = "hello".tap { println(it.toUpperCase()) }.length
    DEBUG("length: $len", "extension")
}

fun DEBUG(message: Any?, title: String? = null) {
    val line = listOf(message, title).filterNotNull().joinToString(" ")
    println("DEBUG: $line")
}
