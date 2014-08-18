package example

import collection.JavaConversions._
import upickle._

import org.nutz.ssdb4j.SSDBs
import org.nutz.ssdb4j.spi.SSDB
import org.nutz.ssdb4j.spi.Response

object Example {

  def main(args: Array[String]): Unit = {
    val ssdb: SSDB = SSDBs.simple()
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

    val resp1 = ssdb.info()

    for (str <- resp1.listString()) {
      println(str)
    }
  }
}
