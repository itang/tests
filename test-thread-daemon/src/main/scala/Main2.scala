object Main2 extends App with Util {
  debug("main2")

  addShutDownHookForTest()

  val t = createThreadForTest()
  t.setDaemon(true)
  t.start()

  debug("main2")
}
//output:
/*
DEBUG Thread[main,5,main] 2014-08-09 20:56:45: main2
DEBUG Thread[main,5,main] 2014-08-09 20:56:45: main2
DEBUG Thread[Thread-0,5,main] 2014-08-09 20:56:45: Shutdown
*/