package foo

import java.lang.management.ManagementFactory
import scala.sys.process._

object Example {
  def main(args: Array[String]): Unit = {
    println(s"""JAVA_OPTS:${System.getenv().get("JAVA_OPTS")}""")

    val cmds = Array(
      s"jcmd $getProcessId GC.heap_info",
      s"jmap -heap $getProcessId",
      s"jhsdb jmap --heap --pid $getProcessId",
      s"jcmd $getProcessId VM.flags",
      s"jinfo -flags $getProcessId"
    )

    for (cmd <- cmds) {
      println(cmd)
      cmd.!
      println()
    }

    scala.io.StdIn.readLine()
  }

  lazy val getProcessId: String = {
    val pname = ManagementFactory.getRuntimeMXBean().getName()
    println("process name = " + pname)

    pname.indexOf("@") match {
      case i if i != -1 => pname.substring(0, i)
      case _            => pname
    }
  }
}
