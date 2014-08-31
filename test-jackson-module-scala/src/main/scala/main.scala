/**
 * main.scala
 */

import scatang._
import com.fasterxml.jackson.databind.ObjectMapper
import scatang._
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object Main {

  def main(args: Array[String]): Unit = {
    val values = List(
      'name -> "itang",
      ("name" -> "itang"),
      Map("name" -> "itang"),
      "itang" :: "tqibm" :: Nil)

    val om = new ObjectMapper
    test(om, values);

    println("*" * 50)

    val omScala = new ObjectMapper() with ScalaObjectMapper
    omScala.registerModule(DefaultScalaModule)
    test(omScala, values);
  }

  private def test(om: ObjectMapper, values: List[Any]) =
    for (v <- values) {
      val json = om.writeValueAsString(v) tap println
      om.readValue(json, classOf[Any]) tap println
    }
}
