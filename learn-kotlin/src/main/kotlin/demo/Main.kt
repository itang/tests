package demo

fun main(args: Array<String>) {
    println("Hello, Kotlin")
    println(parseInt("100"))
    println(parseInt("100x"))

    val s = getValue() as String // hacked
    println(pi(s))
}

fun getValue(): String? = "1000d"

fun parseInt(s: String?): Int? =
        if (s == null) {
            null
        } else {
            try {
                Integer.parseInt(s)
            } catch(e: Exception) {
                println("WARN: ${e.message}")
                null
            }
        }

fun pi(s: String): Int? = parseInt(s)
