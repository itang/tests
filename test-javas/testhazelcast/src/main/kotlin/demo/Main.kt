package demo

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast

fun main(args: Array<String>) {
    val config = Config()
    val h = Hazelcast.newHazelcastInstance(config)
    val m = h.getMap<String, String>("my-map")
    m["name"] = "itang"

    println(m["name"])

    val m2 = h.getReplicatedMap<String, String>("my-shared-map")
    m2["name"] = "itang"
    println(m2["name"])
}
