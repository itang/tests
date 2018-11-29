package demo

fun main(args: Array<String>) {
    println(Thread.currentThread())

    val t = object : Thread() {
        override fun run() {
            println(Thread.currentThread())
            Thread.sleep(1000)
            throw  RuntimeException("panic in sub thread")
        }
    }

    t.start()

    Thread.sleep(3 * 1000)

    val t2 = object : Thread() {
        override fun run() {
            throw java.lang.RuntimeException("panic from t2")
        }
    }
    t2.start()

    t2.setUncaughtExceptionHandler { th, ex -> println("Uncaught exception: $ex") }

    println("exit")
}
