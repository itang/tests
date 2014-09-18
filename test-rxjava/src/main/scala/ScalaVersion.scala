package test_rxjava

import scala.language.postfixOps

import rx.lang.scala._
//import rx.lang.scala.schedulers._
import scala.concurrent.duration._

import scatang._

object ScalaVersion extends App {
  val numberObservable: Observable[Int] = Observable.from(Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))

  numberObservable.subscribe(
    n ⇒ println(Thread.currentThread() + " " + n),
    e ⇒ e.printStackTrace(),
    () ⇒ println("done"))

  val o = Observable.interval(200 millis).take(5)
  o.subscribe(n ⇒ println(Thread.currentThread() + " " + "n = " + n))
  Thread.sleep(2000)
}
