package demo

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    val manager = DelayTaskManagerImpl()

    log("Start...")
    (0..5).forEach {
        manager.addDelayTask(DelayTaskImpl(it.toString(), it.toLong(), TimeUnit.SECONDS, manager))
    }
    log("Finished batch add tasks")

    Thread.sleep(20 * 1000)
    manager.shutdownAndAwait(10, TimeUnit.SECONDS)
    log("Exit")
}


interface DelayTaskManager {
    fun addDelayTask(task: DelayTask)
    fun removeDelayTask(id: String)
    fun shutdownAndAwait(v: Long, unit: TimeUnit)
}

interface DelayTask : Runnable {
    val id: String
    val delay: Long
    val timeUnit: TimeUnit
}

class DelayTaskManagerImpl(val threadPoolSize: Int = 2) : DelayTaskManager {
    val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(threadPoolSize)
    val taskMap = ConcurrentHashMap<String, DelayTask>()

    override fun addDelayTask(task: DelayTask) {
        taskMap.put(task.id, task)
        scheduler.schedule(task, task.delay, task.timeUnit)
    }

    override fun removeDelayTask(id: String) {
        log("Remove task $id")
        log("Before task number: ${this.taskMap.size}")
        taskMap.remove(id)
        log("End task number: ${this.taskMap.size}")
    }

    override fun shutdownAndAwait(v: Long, unit: TimeUnit) {
        log("Shutdown, Await...")
        scheduler.shutdown()
        scheduler.awaitTermination(v, unit)
    }
}

data class DelayTaskImpl(override val id: String, override val delay: Long, override val timeUnit: TimeUnit, val manager: DelayTaskManager, val attempts: Int = 1, val maxAttempts: Int = 3) : DelayTask {
    init {
        assert(attempts > 0, { "attempts should be greater than zero" })
    }

    override fun run() {
        try {
            log("Tasker $id running, attempts: $attempts ...")

            val mockTime = Random().nextInt(3000).toLong()
            log("executing $mockTime millis")
            if (mockTime < 1500) {
                throw RuntimeException("模拟执行出异常")
            }
            Thread.sleep(mockTime)

            log("Tasker $id finished.")
        } catch(e: Exception) {
            log("ERROR ${e.message}")
            if (attempts == maxAttempts) {
                log("超过最大尝试次数, 退出执行...")
                manager.removeDelayTask(id)
            } else {
                log("重新加入执行队列...")
                manager.addDelayTask(this.copy(attempts = this.attempts + 1, delay = 1, timeUnit = TimeUnit.MILLISECONDS))
            }
        }
    }
}

fun log(msg: String) {
    println("${SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(Date())}> $msg")
}
