package demo

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.DelayQueue
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

class DelayItem<out T>(submit: T, timeout: Long) : Delayed {
    companion object {
        val NANO_ORIGIN = System.nanoTime()
        val sequencer = AtomicLong(0)

        fun now() = System.nanoTime() - NANO_ORIGIN
    }

    private val time: Long
    val item: T
    private val sequenceNumber: Long

    init {
        time = now() + timeout
        item = submit
        sequenceNumber = sequencer.getAndIncrement()
    }

    override fun getDelay(unit: TimeUnit): Long {
        val d = unit.convert(time - now(), TimeUnit.NANOSECONDS)
        return d
    }

    override fun compareTo(other: Delayed?): Int {
        if (other == null) {
            return 1
        }
        if (other == this) {
            return 0
        }

        if (other is DelayItem<*>) {
            val diff = time - other.time
            return when {
                diff < 0 -> -1
                diff > 0 -> 1
                sequenceNumber < other.sequenceNumber -> -1
                else -> 1
            }
        }

        val d = getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS)
        return when {
            d == 0L -> 0
            d < 0 -> -1
            else -> 1
        }
    }
}

class Cache<in K, V> {
    private val cacheObjMap = ConcurrentHashMap<K, V>()
    private val q = DelayQueue<DelayItem<Pair<K, V>>>()
    private val daemonThread: Thread

    init {
        daemonThread = thread(isDaemon = true, name = "Cache Daemon", block = this::daemonCheck)
    }

    private fun daemonCheck() {
        while (true) {
            try {
                val delayItem = q.take()
                if (delayItem != null) {
                    val pair = delayItem.item
                    cacheObjMap.remove(pair.first, pair.second)
                }
            } catch(e: InterruptedException) {
                println(e)
                break
            }
        }
    }

    fun put(key: K, value: V, time: Long, unit: TimeUnit) {
        val oldValue = cacheObjMap.put(key, value)
        if (oldValue != null) {
            q.removeIf {
                it.item.first == key
            }
        }
        val nanoTime = TimeUnit.NANOSECONDS.convert(time, unit)
        q.put(DelayItem(Pair(key, value), nanoTime))
    }

    fun get(key: K): V? {
        return cacheObjMap[key]
    }
}

fun main(args: Array<String>) {
    val cache = Cache<Int, String>()
    cache.put(1, "hello", 3, TimeUnit.SECONDS)
    Thread.sleep(1000 * 2)
    println(cache.get(1))

    Thread.sleep(1000 * 2)
    println(cache.get(1))
}

