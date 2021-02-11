package effectivescala

import scala.annotation.tailrec
import scala.util.Random

object Main {
  def main(args: Array[String]): Unit = {
    println("effective scala")
    try {
      User.get(1)
    } catch {
      case e: Exception => println(e.getMessage)
      case e: Error => println(e.getMessage)
    }

    import Util.square
    println(square(100))

    val ret1 = List(Some(1), None, Some(200)).map {
      case Some(x) => x
      case None => 0
    }.sum
    println(ret1)

    (0 until 5).foreach(_ => println(IntMakerImpl()))

    // 使用不可变集合，明确地引用可变集合的命名空间。
    import scala.collection.mutable
    val set = mutable.Set[Int]()
    set.add(100)
    println(set)

    val seq = Seq(1, 2, 3)
    println(seq)
    val imSet = Set(1, 2, 3)
    println(imSet)

    val m = Map("name" -> "itang", "age" -> 100)
    println(m.getClass)
    m.getClass.getTypeParameters.foreach(println)
    println(m)

    import scala.jdk.CollectionConverters._
    List(1, 2, 3).asJava.forEach(println)

    // 线程提供了一种表达并发的方式：他们给你独立的，堆共享的（heap-sharing）由操作系统调度的执行上下文
    // 在Java里线程的创建是昂贵的， 是一种必须托管的资源，通常借助于线程池。这是对程序员创造了额外的复杂
    // 也造成高度的耦合：很难从所使用的基础资源中分离应用逻辑。
    // 当创建高度分散（fan-out)的服务时这种复杂度尤其明显：每个输入请求导致一大批对另一层系统的请求。
    // 在这些系统中，线程池必须被托管以便根据每一层请求的比例来平衡：一个线程池的管理不善
    // 会导致另一个线程池页出现问题。
    // 一个健壮系统必须考虑超时和取消，两者都需要引入更多“控制”线程，使问题更加复杂

    // 在字节码层面实现为一个异常的捕获、声明（catching/throwing)对，用在频繁的执行的代码中
    // 会有性能影响
    def inner(): Unit = {
      List(1, 2, 3).foreach { it =>
        if (it > 2) {
          return;
        }
        println(it)
      }

      inner()
    }

    val tree = Node(left = Node(left = Leaf(10), right = Leaf(20)), right = Leaf(30))
    import Tree1._
    val tree1 = Node1(left = Node1(left = Leaf1(10), right = Leaf1(20)), right = Leaf1(30))
    println(tree)
    println(tree1)

    val r1 = Option(null)
    r1 match {
      case None => println("none")
      case Some(_) => println("some value")
    }

    val pf: PartialFunction[Int, String] = {
      case i if i % 2 == 0 => "even"
    }
    val tf = pf.orElse {
      case i if i % 2 == 1 => "odd"
    }
    println(tf(100))
    println(tf(101))
  }
}

case class User()

object User {
  def get(id: Int): Option[User] = ???
}

object Util {
  def square(x: Int) = x * x
}

//不可变（Immutable）集合应该是协变的。接受容器化类型得方法应该适当地降级（downgrade）集合
trait Collection[+T] {
  def add[U >: T](other: U): Collection[U]
}

//可变（mutable）集合应该是不可变的（invariant）。协变对于可变集合是典型无效的。

//类型别名
type IntMaker = () => Int
val IntMakerImpl: IntMaker = () => Random.nextInt() % 100

sealed trait Tree[T]

case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def toString: String =
    new StringBuilder().append("tree{").append("left:").append(left).append(", right:").append(right).append("}").toString()
}

case class Leaf[T](value: T) extends Tree[T] {
  override def toString: String = s"value: $value"
}

enum Tree1[T] {

  case Node1(left: Tree1[T], right: Tree1[T])

  case Leaf1(value: T)

  override

  def toString: String = this match {
    case Leaf1(value) => s"value1= $value"
    case Node1(left, right) => s"tree1{left1: ${left}, right1: ${right}}"
  }
}