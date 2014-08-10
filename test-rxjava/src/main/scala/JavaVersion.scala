package test_rxjava

import rx.Observable
import rx.functions.Action0
import rx.functions.Action1

object JavaVersion extends App {

  val numbers = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  val numberObservable: Observable[Int] = Observable.from(numbers: _*)
  numberObservable.subscribe(new Action1[Int]() {
    override def call(incomingNumber: Int) {
      if (incomingNumber == 8) {
        throw new RuntimeException("error")
      }
      println(Thread.currentThread() + " " + incomingNumber)
      Thread.sleep(500)
    }
  }, new Action1[Throwable]() {
    override def call(error: Throwable) {
      println("Error in synchronous observable")
    }
  }, new Action0() {
    override def call() {
      println("This observable is finished")
    }
  })

  numberObservable.subscribe(new Action1[Int]() {
    override def call(incomingNumber: Int) {
      println(Thread.currentThread() + " " + s"$incomingNumber : ${('a' + incomingNumber).asInstanceOf[Char]}")
    }
  })

}
