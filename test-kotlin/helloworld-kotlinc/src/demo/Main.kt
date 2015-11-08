package demo

/**
 * test kotlin.
 */
fun main(args: Array<String>) {
    println("args.size: ${args.size}")

    val name = if(args.size > 0) args[0] else "itang"
    val p = Person(name)
    val ret = p.sayHello()

    println("ret:$ret")
}
