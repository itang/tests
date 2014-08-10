package test_rxjava

import scala.language.postfixOps

import rx.lang.scala._
//import rx.lang.scala.schedulers._
import scala.concurrent.duration._

object ScalaVersion extends App {
  val numbers = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  val numberObservable: Observable[Int] = Observable.items(numbers: _*)

  numberObservable.subscribe(
    n => println(Thread.currentThread() + " " + n),
    e => e.printStackTrace(),
    () => println("done"))

  val o = Observable.interval(200 millis).take(5)
  o.subscribe(n => println(Thread.currentThread() + " " + "n = " + n))
  Thread.sleep(2000)
}
