package collections

import java.util.Arrays

object Collections {
  def main(args: Array[String]): Unit = {
    val a = Array(1, 2, 3)
    val b = Array(1, 2, 3)
    println(a == b)
    println(a.equals(b))
    println(a.eq(b))
    println(a.sameElements(b))
    println(Arrays.equals(a, b))

    val list1 = List(1, 2, 3, 4)
    val list2 = List(1, 2, 3, 4)
    assert(list1 == list2)
    println(list1)
    println(list1.contains(3))
    assert(1 == list1.head)

    //Tuple
    val hostPort = ("localhost", 80)
    val (host, port) = hostPort
    assert(host == "localhost")
    assert(port == 80)
    assert(hostPort._1 == host)
    assert(hostPort.productIterator.toList == List(host, port))

    hostPort match {
      case ("localhost", port) => println(port)
      case _ => println("else")
    }

    val hostPort2: (String, Int) = host -> port
    assert(hostPort == hostPort2)


    //Map
    val m1 = Map(1 -> 2)
    assert(m1(1) == 2)
    val m2 = Map("foo" -> "bar")
    assert(m2("foo") == "bar")
    assert((m2.getOrElse("foo", "some") == "bar"))

    val o1: Option[String] = m2.get("foo")
    assert(o1.isDefined)
    assert(o1 == Some("bar"))
    o1 match {
      case Some(v) => assert(v == "bar")
      case None => assert(false)
    }
    assert(o1.get == "bar")
    val r1 = o1.getOrElse("some")
    println(r1)
    assert(r1 == "bar")
    assert(m2.get("some").getOrElse("some") == "some")


    //functions Combinators
    val numbers = List(1, 2, 3, 4)
    val numbers1 = numbers.map((i: Int) => i * 2)
    val numbers2 = numbers.map(i => i * 2)
    val numbers3 = numbers.map(_ * 2)
    assert(numbers2 == List(2, 4, 6, 8))
    assert(numbers1 == numbers2)
    assert(numbers2 == numbers3)

    def addOne(x: Int) = x + 1

    val numbers4 = numbers.map(addOne)
    assert(numbers4 == List(2, 3, 4, 5))

    //foreach
    numbers.foreach((i: Int) => println(i))
    numbers.foreach(println(_))
    numbers.foreach(i => println(i))
    numbers.foreach(println)

    //filter
    val nn1 = numbers.filter((i: Int) => i % 2 == 0)
    assert(nn1 == List(2, 4))

    //zip
    val zz = List(1, 2, 3).zip(List("a", "b", "c"))
    assert(zz == List((1, "a"), (2, "b"), (3, "c")))
    println(zz.toMap)
    assert(zz.toMap == Map((1, "a"), (2, "b"), (3, "c")))

    //partition
    val nn2 = numbers.partition(_ % 2 == 0)
    println(nn2)

    //find
    val r2 = numbers.find(_ > 5)
    assert(r2.isEmpty)

    val r3 = numbers.drop(2)
    assert(r3 == List(3, 4))
    assert(numbers.dropWhile(i => i % 2 == 1) == List(2, 3, 4))

    //foldLeft
    assert(numbers.foldLeft(0)((m, n) => m + n) == 0 + 1 + 2 + 3 + 4)
    //foldRight
    numbers.foldRight(0) { (m: Int, n: Int) => println("m: " + m + " n: " + n); m + n }

    //flatten
    assert(List(List(1, 2), List(3)).flatten == List(1, 2, 3))

    //flatMap : 先map再flatten
    val nestedList = List(List(1, 2), List(3, 4))
    println(nestedList.flatMap(x => x.map(_ * 2)))

    //每个函数组合子都可以使用fold方法来实现
    def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
      numbers.foldRight(List())({ (x: Int, xs: List[Int]) =>
        fn(x) :: xs
      })
    }

    val r5 = ourMap(numbers, _ * 2)
    println(r5)
    assert(r5 == List(2, 4, 6, 8))

    val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)

    val mr0 = extensions.filter((n, v) => v <= 100)
    assert(mr0 == Map(("steve", 100)))

    val mr1 = extensions.filter {
      case (name, extension) => extension <= 100
    }
    assert(mr0 == mr1)
  }

}