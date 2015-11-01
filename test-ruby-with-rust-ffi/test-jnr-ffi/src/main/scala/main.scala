/**
 * main.scala
 */

import jnr.ffi.LibraryLoader

trait Fibs {
  def fibonacci(i: Integer) : Integer
}

object Main {
  def main(args: Array[String]): Unit = {
    val path = "../fibonacci/target/release/libfibonacci.so"
    val fibs: Fibs = LibraryLoader.create(classOf[Fibs]).load(path)
    println(fibs.fibonacci(10))
  }
}
