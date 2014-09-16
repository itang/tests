package zt_exec

//import scala.collection.mutable.ArrayBuffer

import org.zeroturnaround.exec.ProcessExecutor
import org.zeroturnaround.exec.ProcessResult

object ZtExec {

  def exec(cmd: String, args: String*)(read: Boolean = false): ProcessResult = {
    //val buf = ArrayBuffer(cmd) ++= args
    var buf = cmd :: args.toList

    val p = new ProcessExecutor().command(buf: _*)
    p.readOutput(read).execute()
  }
}