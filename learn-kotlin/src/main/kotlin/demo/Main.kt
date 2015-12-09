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

fun test1() {
    fun getValue(): String? = "1000d"

    fun parseInt(s: String?): Int? {
        if (s == null) {
            return null
        } else {
            try {
                return Integer.parseInt(s)
            } catch(e: Exception) {
                println("WARN: ${e.message}")
                return null
            }
        }
    }

    fun pi(s: String): Int? = parseInt(s)

    INFO("Hello, Kotlin")
    INFO(parseInt("100"))
    INFO(parseInt("100x"))

    val s = getValue() as String // hacked
    INFO(pi(s))
}

fun test_extension() {
    val len = "hello".tap { println(it.toUpperCase()) }.length
    INFO("length: $len", "extension")
}

fun INFO(message: Any?, title: String? = null) {
    if (title == null) {
        println("INFO: $message")
    } else {
        println("INFO: $title $message")
    }
}
