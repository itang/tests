import java.util.concurrent.TimeUnit

import com.github.benmanes.caffeine.cache.{Caffeine, LoadingCache, RemovalCause}
//import scala.collection.JavaConverters._
import util._

import scala.language.postfixOps

object Main {
  def main(args: Array[String]): Unit = time {
    val cache: LoadingCache[String, String] = Caffeine.newBuilder()
      .recordStats()
      .maximumSize(40).removalListener(
      (key: String, value: String, cause: RemovalCause) => {
        println(s"key:$key, value:$value, cause: $cause")
      }
    )
      .expireAfterWrite(10, TimeUnit.SECONDS).build(
      (key: String) => {
        println(s"load $key...")
        key match {
          // case "z" => throw new RuntimeException("is Z, throw error")
          case it if 'o' to 'z' contains it.toCharArray.head => null
          case it => it.toUpperCase()
        }
      }
    )


    for (c <- 'a' to 'z') {
      cache.get(c.toString) |> (it => println(s"\t$c: ${it}"))
      // cache.put(c.toString, c.toString)
    }
    println(cache.stats())
    println("*" * 100)

    class MyThread extends Thread {
      var i = 0

      override def run(): Unit = {
        while (true) {
          println(i)
          i += 1
          Thread.sleep(1000)
        }
      }
    }
    val myThread = new MyThread();
    myThread.start()


    for (c <- 'a' to 'z') {
      cache.get(c.toString) |> (it => println(s"\t$c: ${it}"))
      // cache.put(c.toString, c.toString)
    }
    println(cache.stats())

    Thread.sleep(TimeUnit.SECONDS.toMillis(20))

    println("*" * 100)
    for (c <- 'a' to 'z') {
      cache.get(c.toString) |> (it => println(s"\t$c: ${it}"))
      // cache.put(c.toString, c.toString)
    }
    println(cache.stats())

    println(cache.get("a"))
  }
}
