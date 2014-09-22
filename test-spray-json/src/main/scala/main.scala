/**
 * main.scala
 */

import java.util.Date

import spray.json.DefaultJsonProtocol
import spray.json.JsNumber
import spray.json.JsValue
import spray.json.RootJsonFormat
import spray.json.pimpAny
import spray.json.pimpString

object Main {

  case class User(id: Long, name: String, birthday: Date)

  implicit val DateFormat = new RootJsonFormat[java.util.Date] {
    lazy val format = new java.text.SimpleDateFormat()
    def read(json: JsValue): Date = new Date(json.compactPrint.toLong)
    def write(date: Date) = JsNumber(date.getTime())
  }

  object UserProtocol extends DefaultJsonProtocol {
    implicit val userFormat = jsonFormat3(User)
  }

  case class Thing(some: String)
  object ThingProtocol extends DefaultJsonProtocol {
    implicit val thingFormat = jsonFormat1(Thing)
  }

  def main(args: Array[String]): Unit = {
    val source = """{ "some": "JSON source" }"""
    val jsonAst = source.parseJson
    val json = jsonAst.prettyPrint
    println(json)
    println(jsonAst.compactPrint)
    val jsonAst2 = json.parseJson
    println(jsonAst2)

    import ThingProtocol._
    val thing = jsonAst2.convertTo[Thing]
    println(thing.some)

    import UserProtocol._
    val user = User(1, "itang", new Date)
    val userJsonAst = user.toJson
    val userJson = userJsonAst.prettyPrint
    println(userJson)
    val userJsonAst2 = userJson.parseJson
    val user2 = userJsonAst2.convertTo[User]
    println(user2)
    assert(user2.id == 1)
  }
}
