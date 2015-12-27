package demo

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit

data class Key(val  key: String)
data class Graph(val value: String) {
    companion object {
        fun createExpensiveGraph(key: Key): Graph {
            return Graph("value:${key.key}")
        }
    }
}

fun main(args: Array<String>) {
    val cache: Cache<Key, Graph> = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .recordStats()
            .maximumSize(10000)
            .build()
    // Lookup an entry, or null if not found
    val key = Key("name")
    val graph: Graph? = cache.getIfPresent(key)
    println("graph: $graph")

    // Lookup and compute an entry if absent, or null if not computable
    val graph2 = cache.get(key, { k -> Graph.createExpensiveGraph(k) })
    println("graph2 $graph2")


    // Insert or update an entry
    cache.put(key, graph2)
    // Remove an entry
    cache.invalidate(key)

    println(cache.stats())
}
