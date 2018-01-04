
object Main {

  def main(args: Array[String]): Unit = {
    println("Hello, World!")

    val t = new Thread(() => {
      var i = 0
      while (!Thread.currentThread().isInterrupted) {
        try {
          println(s"$i ee")
          Thread.sleep(1000)
          i += 1
          if(Thread.currentThread().isInterrupted){
            println("Thread.currentThread().isInterrupted")
          }
        } catch {
          case e: InterruptedException => println(s"${Thread.currentThread()} $e")
            throw e
        }
      }
    })
    t.start()

    Thread.sleep(5 * 1000)
    t.interrupt()

    println(t.isAlive)
    println(t.isDaemon)
    println(t.isInterrupted)

    t.join(1000)
    Thread.sleep(2000)
    println("end")
  }
}