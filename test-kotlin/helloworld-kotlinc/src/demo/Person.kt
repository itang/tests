package demo

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.*

interface Helloable {
    fun sayHello(): Int
}

class Person (val name: String, var age: Int = 10, val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) : Helloable {
    public override fun sayHello(): Int {
        lock.write {
            println("${Thread.currentThread()}: Hello, ${name}:${age} with ${lock}")
            age += 1
            return age
        }
    }
}
