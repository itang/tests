package tutorial.webapp

import java.io.Closeable

package object util {

  import java.text.SimpleDateFormat
  import java.util.Date

  implicit class DateOps(d: Date) {
    def format(pattern: String = "yyyyMMdd"): String = new SimpleDateFormat(pattern).format(d)
  }

  def time[T](block: => T): T = {
    val start = System.currentTimeMillis()
    val ret = block
    val end = System.currentTimeMillis()
    println(s">>Cost time: ${end - start}ms")
    ret
  }

  def using[A <: Closeable, B](target: A)(block: A => B): B =
    try {
      val ret = block(target)
      target.close()
      ret
    } catch {
      case e: Exception => throw e
    }

  implicit class Pipe[T](val v: T) extends AnyVal {
    def |>[U](f: T => U) = f(v)

    // Additional suggestions:
    def $$[U](f: T => U): T = {
      f(v);
      v
    }

    def #!(str: String = ""): T = {
      println(str + v);
      v
    }
  }

}