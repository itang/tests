package demo

import java.util.concurrent.locks.ReentrantReadWriteLock
import demo.concurrent._

trait Helloable {
  def sayHello(): Integer
}

class Person(val name: String, var age: Int = 10, val lock: ReentrantReadWriteLock = new ReentrantReadWriteLock) extends Helloable {
  override def sayHello(): Integer =
    lock.write {
      println(s"${Thread.currentThread()}: Hello, ${name}:${age} with ${lock}")
      age += 1
      age
    }
}
