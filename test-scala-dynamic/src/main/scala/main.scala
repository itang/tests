/**
 * main.scala
 */

import scala.language.dynamics
import scala.language.implicitConversions

object Main {

  class MyDynamic(a: Any) extends Dynamic {

    def applyDynamic(name: String)(args: Any*) = {
      invokeMethod(methodName(name), args: _*)
    }

    private def methodName(origName: String): String = origName match {
      case name if name.startsWith("get_") => "get" + name.substring(4).capitalize
      case name if name.startsWith("set_") => "set" + name.substring(4).capitalize
      case _ => origName
    }

    private def invokeMethod(name: String, args: Any*) = {
      try {
        if (args.isEmpty) {
          val method = a.getClass.getMethod(name)
          method.invoke(a)
        } else {
          val argsClazzes = args.map(it => it.getClass)
          println(argsClazzes)
          val method = a.getClass.getMethod(name, argsClazzes: _*)
          //println(method + " " + args + " " + args.toArray)
          //          if(name=="echo"){
          //            method.invoke(a, args(0).asInstanceOf[String])
          //          }else
          val as = args.map(it => it.asInstanceOf[AnyRef])
          method.invoke(a, as: _*)
        }
      } catch {
        case e: java.lang.NoSuchMethodException => println(s"no such method: $name, ignore")
        case e: Exception => throw e
      }
    }
  }

  class User {
    def getName() = "itang"

    def echo(msg: String): Unit = println(msg)
  }

  def main(args: Array[String]): Unit = {
    val user = new MyDynamic(new User)
    println(user.get_class())
    println(user.getName())
    println(user.get_name())
    user.echo("hello")
  }
}

