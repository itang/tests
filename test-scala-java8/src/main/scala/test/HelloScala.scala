package test

object HelloScala extends App {
  new Thread((() ⇒ {
    println("Hello,World (From Scala)")
  }): Runnable).start()

  List("Hello", "World") map (_.toUpperCase()) foreach println
}
