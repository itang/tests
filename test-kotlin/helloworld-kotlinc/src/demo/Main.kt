package demo

/**
 * test kotlin.
 */
fun String.rr(times: Int): String {
    return repeat(times)
}

fun main(args: Array<String>) {
    println("args.size: ${args.size}")

    val name = if(args.size > 0) args[0] else "itang"
    val p = Person(name)

    fun say() {
        val age = p.sayHello()
        println("*" rr 20)
        println("${Thread.currentThread()}: ${p.name}/$age")
    }

    val ts = arrayOf(Thread({say()}), Thread({say()}), Thread({say()}))

    ts.map {
        it.start()
        it
    }.forEach { it.join() }
    //for(t in ts) {
    //    t.start()
    //    t.join()
    //}
}
