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
import spray.json.JsString
import spray.json.JsObject
import spray.json.JsBoolean
import spray.json.JsTrue
import spray.json.JsFalse
import spray.json.JsNull
import spray.json.JsArray

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

  val source = """{ "some": "JSON source" }"""

  def main(args: Array[String]): Unit = {
    ast()
    println("*" * 100)
    converts()
  }

  def ast() = {
    val jsonAsts = List(source.parseJson, "1".parseJson, "\"1\"".parseJson, "false".parseJson, "true".parseJson, "null".parseJson, "[1,2]".parseJson)
    for (jsonAst ← jsonAsts) {
      jsonAst match {
        case JsNumber(i) ⇒ println("number:" + i)
        case JsString(s) ⇒ println("string:" + s)
        case JsObject(o) ⇒ println("object:" + o)
        //case JsBoolean(b) ⇒ println("boolean:" + b)
        case JsFalse ⇒ println("boolean false")
        case JsTrue ⇒ println("boolean true")
        case JsNull ⇒ println("null")
        case JsArray(arr) ⇒ println("array:" + arr)
        //case e: JsValue ⇒ println("jsvalue:" + e)
      }
    }
    val c = """{"user":{"name":"itang", "age":20}}""".parseJson
    c match {
      case JsObject(o) ⇒ {
        println(o)
        for ((k, v) ← o) {
          println(s"$k: $v(${v.getClass})")
        }
        o("user") match {
          case JsObject(m) ⇒ println(m)
          case _ ⇒
        }
      }
      case _ ⇒ println("unknown")
    }
  }

  def converts() = { // jons -> ast : parseJson
    val jsonAst = source.parseJson
    // ast -> json (String): prettyPrint | compactPrint
    val json = jsonAst.prettyPrint
    println(json)
    println(jsonAst.compactPrint)
    val jsonAst2 = json.parseJson
    println(jsonAst2)

    //ast -> object : convertTo[T]
    import ThingProtocol._
    val thing = jsonAst2.convertTo[Thing]
    println(thing.some)

    import UserProtocol._
    val user = User(1, "itang", new Date)

    // Object -> ast : toJson
    val userJsonAst = user.toJson
    val userJson = userJsonAst.prettyPrint
    println(userJson)
    val userJsonAst2 = userJson.parseJson
    val user2 = userJsonAst2.convertTo[User]
    println(user2)
    assert(user2.id == 1)
  }

}
