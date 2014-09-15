package apache_exec

import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.exec.ExecuteWatchdog

trait Watch { self ⇒
  def toOption: Option[ExecuteWatchdog]
}

case class Watched(timeout: Int) extends Watch {
  override def toOption: Option[ExecuteWatchdog] = Some(new ExecuteWatchdog(timeout))
}

object NoWatch extends Watch {
  override def toOption: Option[ExecuteWatchdog] = None
}

object ApacheExec {

  def exec(line: String, watch: Watch = NoWatch): Int = {
    val commandLine = CommandLine.parse(line)
    val executor = new DefaultExecutor()

    watch.toOption.map { wd ⇒
      executor.setWatchdog(wd)
    }

    executor.execute(commandLine)
  }
}