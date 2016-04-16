package demo

import java.util.*
import java.util.concurrent.*

class SendTask(val id: Int, val value: Int) : Delayed {

    val end = Date().time + value * 1000

    override fun getDelay(unit: TimeUnit): Long {
        val now = Date().time
        return end - now
    }

    override fun compareTo(other: Delayed?): Int =
            when (other) {
                is SendTask -> this.value - other.value
                else -> 0
            }
}

fun main(args: Array<String>) {
    val es: ExecutorService = Executors.newSingleThreadExecutor()
    val dq: DelayQueue<SendTask> = DelayQueue<SendTask>()

    val map = ConcurrentHashMap<Int, Int>()
    for (i in 0..10 - 1) {
        dq.offer(SendTask(i, Random().nextInt(5)))
    }

    println("try exec tasks: ${dq.size}")

    es.submit {
        var i = 0
        while (dq.isNotEmpty()) {
            println()
            println("dq.isNotEmpty(): ${dq.isNotEmpty()}")
            val task = dq.take()
            val count = map.compute(task.id, { k, v ->
                if (v == null) {
                    1
                } else {
                    v + 1
                }
            })

            if (count < 2) { // 重放
                dq.offer(task)
            }

            println("$i send task-${task.id} value:${task.value}...")
            println("dq.isNotEmpty(): ${dq.isNotEmpty()}")
            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(100))
            i += 1
        }
    }

    es.shutdown()
    es.awaitTermination(10, TimeUnit.SECONDS)
}
