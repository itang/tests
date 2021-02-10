import scala.scalanative.unsafe.{CLongLong, extern, link}

@extern
object myapi {
  def add3(in: CLongLong): CLongLong = extern
}

@link("myrust")
@extern
object myrust {
  def add3(in: CLongLong): CLongLong = extern
}

object Main {

  def main(args: Array[String]): Unit = {
    import myapi._
    println("Hello, world!")
    val res = add3(-3L)
    assert(0L == res)
    println(s"[c]Add3  to -3 $res")

    testrust()
  }

  def testrust(): Unit = {
    import myrust._
    val res = add3(-3L)
    assert(0L == res)
    println(s"[rust]Add3 to -3 $res")
  }
}
