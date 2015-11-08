/**
 * test kotlin.
 */
fun main(args: Array<String>) {
    println("${args.size}")

    val a = A("hello")
    val ret = a.hello()
    println("ret:$ret")
}
