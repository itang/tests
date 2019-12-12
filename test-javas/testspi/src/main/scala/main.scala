import java.util.{Date, ServiceLoader}

import util._
import scala.collection.JavaConverters._

object Main {

  def main(args: Array[String]): Unit = time {
    println(s"args: ${args.mkString(" ")}")
    System.getenv.asScala.filter(it => it._1.startsWith("ENV")).foreach(println)

    "Hello, World"
      .$$(println)
      .|>(_ + ", " + new Date().format("yyyy-MM-dd"))
      .|>(println)

    val services = ServiceLoader.load(classOf[demo.GreetService])
    for (s <- services.iterator().asScala){
      println(s.hello("itang"))
    }
  }
}
