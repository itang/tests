package demo

fun main(args: Array<String>) {
    test1()
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

    println("Hello, Kotlin")
    println(parseInt("100"))
    println(parseInt("100x"))

    val s = getValue() as String // hacked
    println(pi(s))
}
