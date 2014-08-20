package example

import collection.JavaConversions._
import upickle._

import scatang._

import java.util.{ Set => JSet }
import org.nutz.ssdb4j.SSDBs
import org.nutz.ssdb4j.spi.SSDB
import org.nutz.ssdb4j.spi.Response
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Jedis
import redis.clients.jedis.Transaction
import java.util.Random

object Example {

  def main(args: Array[String]): Unit = {
    // use ssdb4j
    val ssdb: SSDB = SSDBs.simple()
    ssdb.flushdb("")

    ssdb.set("name", "itang").check()

    val resp: Response = ssdb.get("name")

    if (!resp.ok()) {
      // ...
    } else {
      println("name=" + resp.asString());
    }

    ssdb.set("m", write(Map("name" -> "itang")))
    println(ssdb.get("m").asString())

    val m = read[Map[String, String]](ssdb.get("m").asString())
    println(m.getOrElse("name", "null"))

    for (str <- ssdb.info().listString()) {
      println(str)
    }

    //use jedis
    // ./ssdb-server ssdb.conf
    val master: JedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 8888)
    withJedis(master) { implicit jedis: Jedis =>
      val name = jedis.get("name")
      printf("name:%s\n", name)

      printf("m: %s\n", jedis.get("m"))

      jedis.zadd("sose", 200, "car")
      jedis.zadd("sose", 2, "bike")
      jedis.zadd("sose", 3, "bike") // this override the front
      jedis.zadd("sose", 10, "train")
      val sose: JSet[String] = jedis.zrange("sose", 0, -1)
      sose.foreach(println)
      assert(sose.size() == 3)
      assert(sose.isInstanceOf[java.util.LinkedHashSet[_]])
    }

    // ./ssdb-server ssdb_slave.conf
    val slave = new JedisPool(new JedisPoolConfig(), "localhost", 8889)
    withJedis(new JedisPool(new JedisPoolConfig(), "localhost", 8889)) { redis =>
      // redis.slaveof("localhost", 8888) // redis.clients.jedis.exceptions.JedisDataException: ERR Unknown Command: slaveof

      println(redis.get("name"))

      try {
        redis.set("some", "somevalue")
        println("some:" + redis.get("some"))
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }

    withJedis(master) { redis =>
      println("some from master: " + redis.get("some"))
    }

    withJedis(slave) { redis =>
      println("some from slave: " + redis.get("some"))
    }

    withJedis(master) { redis =>
      //val status = redis.watch("name", "m")   // ERR Unknown Command: watch
      //println("status:" + status)
      // val t: Transaction = redis.multi()
      //t.mset("age", "18", "address", "sz")
      //t.exec()                                //  ERR Unknown Command: exec
      //println("address:" + redis.get("address"))
      val rets = redis.mget("name", "m")
      rets.foreach(println)

      time {
        for (i <- (1 to 10000)) {
          val value = new Random().nextInt(i)
          redis.zadd("largezset", value, s"item-$i-$value")
        }
      }
    }

    withJedis(master) { redis =>
      time {
        for (v <- redis.zrange("largezset", 9000, 9050)) {
          println(v)
        }
      }
    }

    withJedis(master) { redis =>
      redis.lpush("list", "a", "b")
      for (s <- redis.lrange("list", 0, -1)) {
        println("list:" + s)
      }

      for (i <- 0 to 10) {
        redis.zadd("zset", i, s"member-$i")
      }

      redis.zrange("zset", 0, -1).foreach(println)
      redis.zrevrange("zset", 0, -1).foreach(println)
    }

    master.destroy()
    slave.destroy()
  }

  def withJedis[T](pool: JedisPool)(f: Jedis => T): T = {
    val jedis = pool.getResource()
    try {
      f(jedis)
    } finally {
      if (jedis != null) {
        jedis.close()
      }
    }
  }
}
