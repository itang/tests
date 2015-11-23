package util

import java.text.SimpleDateFormat
import java.util.Date

trait Log {

  def info(msg: String, title: String = this.getClass.getName): Unit = {
    println(s"INFO ${title} - ${this} - ${now} - ${Thread.currentThread.getName} :: $msg")
  }

  def formatDate(d: Date, pattern: String = "yyyy-MM-dd HH:mm:ss") = new SimpleDateFormat(pattern).format(d)

  def now(): String = formatDate(new Date)

}
