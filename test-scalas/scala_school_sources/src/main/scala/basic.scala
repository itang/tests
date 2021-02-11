import java.util

@main def main() = {
  val two = 1 + 1
  println(two)

  var name = "steve"
  println(name)

  def addOne(m: Int): Int = m + 1

  val three = addOne(2)
  assert(three == 3)

  val f1: Int => Int = (x: Int) => x + 1
  assert(f1(100) == 101)

  val fDouble = { (i: Int) =>
    println("Hello, world")
    i * 2
  }
  assert(fDouble(100) == 200)

  def adder(m: Int, n: Int) = m + n

  val add2 = adder(2, _: Int)
  assert(add2(100) == 102)

  def multiply(m: Int)(n: Int): Int = m * n

  assert(multiply(2)(3) == 6)
  val timesTwo = multiply(2) _
  assert(timesTwo(3) == 6)

  val c1 = (adder _).curried
  assert(c1(2)(3) == 5)


  //可变长度参数
  def capitalizeAll(args: String*) = {
    args.map { arg => arg.capitalize }
  }

  assert(Seq("A", "B") == capitalizeAll("a", "b").tap(x => println(x)))

  val a = Array(1, 3, 2, 100, -20, 30, 50)
  bsort(a)
  println(util.Arrays.toString(a))

  class Calulator {
    val brand: String = "HP"

    def add(m: Int, n: Int): Int = m + n
  }

  val calc = new Calulator
  assert(calc.add(1, 2) == 3)
  assert(calc.brand == "HP")

  class C2(brand: String) {
    val color: String = if (brand == "TI") {
      "blue"
    }
    else if (brand == "HP") {
      "black"
    }
    else {
      "white"
    }
  }

  val c2 = C2("HP")
  assert(c2.color == "black")

  abstract class Shape {
    def getArea(): Int
  }

  class Circle(r: Int) extends Shape {
    def getArea(): Int = r * r * 3
  }

  val s = new Circle(2)
  println(s.getArea())

  trait Car {
    val brand: String
  }
  trait Shiny {
    val shineRefraction: Int
  }
  class BMW extends Car with Shiny {
    override val brand: String = "BMW"
    override val shineRefraction: Int = 12
  }
  val bmw = new BMW
  assert(bmw.brand == "BMW")
  assert(bmw.shineRefraction == 12)

  trait Cache[K, V] {
    def get(key: K): V

    def put(key: K, value: V): Unit

    def delete(key: K): Unit
  }

  class CacheImpl[K, V] extends Cache[K, V] {
    private val m = new scala.collection.mutable.HashMap[K, V]

    def get(key: K): V = m(key)

    def put(key: K, value: V): Unit = m.put(key, value)

    def delete(key: K): Unit = m.remove(key)
  }

  val cache = new CacheImpl[String, String]
  cache.put("name", "scala")
  assert(cache.get("name") == "scala")
}

def bsort(t: Array[Int]): Array[Int] = {
  for (i <- (0 until t.length)) {
    for (j <- (0 until t.length - i - 1)) {
      val v = t(j)
      val n = t(j + 1)
      if (v > n) {
        t(j + 1) = v
        t(j) = n
      }
    }
  }
  t
}

extension[T] (t: T) {
  inline def tap(f: T => Unit): T = {
    f(t)
    t
  }
}