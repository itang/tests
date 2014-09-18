package test_rxjava

import rx.Observable
import rx.functions.Action0
import rx.functions.Action1
import collection.JavaConverters._
import rx.schedulers.Schedulers
import java.util.concurrent.Executors

object JavaVersion extends App {

  val numbers = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  val numberObservable: Observable[Int] = Observable.from(numbers.asJava) // from Iterable

  numberObservable.subscribe(new Action1[Int]() {
    override def call(incomingNumber: Int) {
      if (incomingNumber == 8) {
        throw new RuntimeException("error")
      }
      println(Thread.currentThread() + " " + incomingNumber)
      Thread.sleep(100)
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

  def test_repeat() {
    val Stop = new RuntimeException("stop")
    val s = new Action1[Int]() {
      override def call(i: Int) {
        println(Thread.currentThread() + " " + i)
        Thread.sleep(10)
        if (i == 10) { throw Stop }
      }
    }

    val e = new Action1[Throwable]() {
      override def call(error: Throwable) {
        println("Error in synchronous observable")
      }
    }
    val f = Range(1, 11).asJava

    p("count")
    Observable.from(f).repeat(10).subscribe(s, e)

    p("Schedulers.computation()")
    Observable.from(f).repeat(Schedulers.computation()).subscribe(s, e)

    p("Schedulers.newThread()")
    Observable.from(f).repeat(Schedulers.newThread()).subscribe(s, e)

    p("Schedulers.from(Executors.newFixedThreadPool(1))")
    Observable.from(f).repeat(Schedulers.from(Executors.newFixedThreadPool(1))).subscribe(s, e)

    p("Schedulers.immediate()")
    Observable.from(f).repeat(Schedulers.immediate()).subscribe(s, e)

    p("Schedulers.io()")
    Observable.from(f).repeat(Schedulers.io()).subscribe(s, e)

    p("Schedulers.test()")
    Observable.from(f).repeat(Schedulers.test()).subscribe(s, e)
    p("Schedulers.trampoline()")
    Observable.from(f).repeat(Schedulers.trampoline()).subscribe(s, e)
  }
  test_repeat()

  def p(title: String) {
    println(s">repeat($title)")
    println("*" * 60)
    Thread.sleep(160)
  }

}
