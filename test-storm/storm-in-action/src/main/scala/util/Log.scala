package util

import java.text.SimpleDateFormat
import java.util.Date

trait Log {

  def info(msg: String, title: String = this.getClass.getName): Unit = {
    println(s"INFO ${title} - ${this} - ${now} - ${Thread.currentThread.getName} :: $msg")
  }

  private def now(): String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)

}
