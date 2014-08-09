import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit.SECONDS

trait Util {

  implicit class DateWrapper(d: Date) {

    def format(pattern: String) = new SimpleDateFormat(pattern).format(d)

    def formatDefault = format("yyyy-MM-dd HH:mm:ss")

  }

  protected def debug(f: => Any) =
    printf("DEBUG %s %s: %s\n", currentThread(), new Date().formatDefault, f)

  protected def addShutDownHookForTest() =
    Runtime.getRuntime().addShutdownHook(new Thread() {
      override def run(): Unit = debug("Shutdown")
    })

  protected def createThreadForTest() =
    new Thread() {
      override def run(): Unit = {
        debug("new thread")

        val millis = SECONDS.toMillis(5)
        debug(s"sleep $millis millis")
        sleep(5)
        debug(s"end sleep $millis millis")

        debug("new thread exit")
      }
    }

  protected def currentThread() = Thread.currentThread()

  protected def sleep(seconds: Long) = Thread sleep SECONDS.toMillis(seconds)
}
