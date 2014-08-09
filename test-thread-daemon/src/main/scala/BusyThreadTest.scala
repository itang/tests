/**
 * Loop(Forever) Thread Test.
 *
 */
object BusyThreadTest extends App with Util {

  new Thread() {
    private var count = 0

    override def run() {
      while (count < 100) {
        debug(count)
        sleep(1)
        count += 1
      }
    }
  }.start()

  debug("main")

}