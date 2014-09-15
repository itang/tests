/**
 * main.scala
 */

import scatang._

import apache_exec._
import zt_exec._

object Main {

  def main(args: Array[String]): Unit = {
    time {
      val exitValue = ApacheExec.exec("ls -la")
      println(exitValue)

      val exitValue2 = ApacheExec.exec("ls -la", Watched(50 * 1000))
      println(exitValue2)
    }

    section()

    time {
      val ret = ZtExec.exec("ls", "-la")(true)
      println(ret.getExitValue())
      println(ret.outputUTF8())
    }

    section()

    time {
      val ret2 = ZtExec.exec("tail", "-n", "30", "/home/itang/workspace/crp-server/logs/application.log")(true)
      println(ret2.outputUTF8())
    }
  }

  def section() = {
    println("*" * 100)
    println("*" * 100)
  }
}

