import scala.beans.BeanProperty

object type_basics {

  case class User
  (@BeanProperty
   var name: String
  )

  def main(args: Array[String]): Unit = {
    def toList[A](a: A): List[A] = List(a)

    val list1 = toList("a")
    assert(list1 == List("a"))

    //Scala有秩1多态性
    //def foo[A, B](f: A => List[A], b: B) = f(b)
    //def foo[A, B](f: A => List[A])(b: B): List[B] = f(b) 
    /* Found:    (b : B)  Required: A */

    // 变性 Variance
    //Scala的类型系统必须同时解释类层次和多态性
    //类层次结构可以表达子类关系。
    //在混合OO和多态时，一个核心问题是：如果T'是T的一个子类, Container[T'] 应该被看出是Container[T]的子类吗 ？
    // 变性(Variance) 注解允许你表达类层次结构和多态类型之间的关系

    //协变convariant  C[T'] 是C[T]的子类    [+T]
    //逆变contravariant C[T] 是C[T']的子类  [-T]
    //不变invariant     C[T]和C[T']无关    [T]


    //子类型关系的真正含义： 对一个给定的类型T， 如果T'是其子类型， 你能替换它吗？
    class Covariant[+A]

    val cv: Covariant[AnyRef] = new Covariant[String]
    println(cv)

    class Contravariant[-A]

    val cv2: Contravariant[String] = new Contravariant[AnyRef]
    println(cv2)

    trait F1[-T1, +R] extends AnyRef

    class Animal {
      val sound = "rustle"
    }

    class Bird extends Animal {
      override val sound = "call"
    }
    class Chicken extends Bird {
      override val sound: String = "cluck"
    }

    val getTweet: (Bird => String) = ((a: Animal) => a.sound)
    println(getTweet(new Bird))
    println(getTweet(new Chicken))
    //输入参数逆变[-T], 输出参数协变[+T]

    //边界 <:
    def cacophony[T <: Animal](things: Seq[T]) = things map (_.sound)

    println(cacophony(List(new Bird, new Chicken)))

    //下界 >:
    val flock = List(new Bird, new Bird)
    val r0 = new Animal :: flock

    val r1: List[Bird] = new Chicken :: flock // Chicken -> Bird
    val r2: List[Animal] = r1 // 协变
    println(r1)
    println(r2)

    //通配符
    def count[A](l: List[A]) = l.size

    def count1(l: List[_]) = l.size

    def count2(l: List[?]) = l.size

    println(count(List(1, 2, 3)))

    //为通配符类型变量应用边界
    def count0(list: List[? <: Animal]): Int = list.size

    count0(List(new Bird, new Animal))

    //implicit def strToInt(x: String) = x.toInt
    import scala.language.implicitConversions
    given Conversion[String, Int] with {
      def apply(s: String): Int = Integer.parseInt(s)
    }

    val y: Int = "123"
    println(y)

    given string2Double: Conversion[String, Double] = java.lang.Double.parseDouble(_)
    val y2: Double = "123"

    println(implicitly[Ordering[Int]])
  }
}
