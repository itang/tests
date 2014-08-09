/**
 * Common Thread Test.
 *
 */
object CommonThreadTest extends Util {

  def main(args: Array[String]): Unit = {
    debug("main")

    addShutDownHookForTest()

    createThreadForTest().start()

    debug("main")
  }

}
//output:
/*
DEBUG Thread[main,5,main] 2014-08-09 20:56:59: main
DEBUG Thread[main,5,main] 2014-08-09 20:56:59: main
DEBUG Thread[Thread-1,5,main] 2014-08-09 20:56:59: new thread
DEBUG Thread[Thread-1,5,main] 2014-08-09 20:56:59: sleep 5000 millis
DEBUG Thread[Thread-1,5,main] 2014-08-09 20:57:04: end sleep 5000 millis
DEBUG Thread[Thread-1,5,main] 2014-08-09 20:57:04: new thread exit
DEBUG Thread[Thread-0,5,main] 2014-08-09 20:57:04: Shutdown
*/