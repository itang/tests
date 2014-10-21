package test_hazelcast

import com.hazelcast.client.config.ClientConfig
import com.hazelcast.client.HazelcastClient
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.IMap

import collection.JavaConversions._

object client extends App {
  val clientConfig = new ClientConfig()
  clientConfig.getNetworkConfig().setAddresses(List("127.0.0.1:5701"))

  val client: HazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig)
  val map: IMap[Int, String] = client.getMap("customers")
  println(map.size())

  for ((k, v) ← map) {
    println(s"k=$k, v=$v")
  }

  client.shutdown()
}