package test_hazelcast

import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import java.util.{ Map ⇒ JMap, Queue ⇒ JQueue }

object server extends App {
  val cfg = new Config()
  val instance: HazelcastInstance = Hazelcast.newHazelcastInstance(cfg)

  val mapCustomers: JMap[Integer, String] = instance.getMap("customers")
  println("contains 1:" + mapCustomers.containsKey(1))

  mapCustomers.put(1, "Joe")
  mapCustomers.put(2, "Ali")
  mapCustomers.put(3, "Avi")

  println("Customer with key 1:" + mapCustomers.get(1))
  println("Map size:" + mapCustomers.size())

  val queueCustomers: JQueue[String] = instance.getQueue("customers")
  queueCustomers.offer("Tom")
  queueCustomers.offer("Mary")
  queueCustomers.offer("Jane")
  System.out.println("First customer: " + queueCustomers.poll())
  System.out.println("Second customer: " + queueCustomers.peek())
  System.out.println("Queue size: " + queueCustomers.size())
}