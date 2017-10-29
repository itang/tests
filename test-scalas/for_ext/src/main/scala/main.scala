
object Main {

  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    for (i <- A(List(1, 2, 3))) {
      println(i)
    }

    for (i <- A(List("a", "b"))) yield i + "_hello" //map


    for (
      i <- A(List(1, 2, 3)) if i % 2 == 0 //foreach, withFilter
    ) {
      println(i)
    }

    for (
      i <- A(List(1, 2, 3)); //foreach
      y <- List(4, 5, 6)
    ) {
      println(i * y)
    }

    for (
      i <- A(List(1, 2, 3)); // flatMap
      y <- List(4, 5, 6)
    ) yield i * y
  }
}

case class A[A](list: List[A]) {
  def foreach(f: A => Unit): Unit = {
    println("start")
    list.foreach(f)
    println("end")
  }

  def map[B](f: A => B): List[B] = {
    println("map start")
    val ret = list.map(f)
    println(s"end map:$ret")
    ret
  }

  def withFilter(f: A => Boolean) = {
    list.withFilter(f)
  }

  def flatMap[B](f: A => List[B]): List[B] = {
    val ret = list.flatMap(f)
    println(ret)
    ret
  }
}
