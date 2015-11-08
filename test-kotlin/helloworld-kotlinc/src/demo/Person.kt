package demo

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.*

interface Helloable {
    fun sayHello(): Int
}

class Person (val name: String, val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) : Helloable {
    var age = 10

    public override fun sayHello(): Int {
        lock.read {
            println("Hello, ${name}:${age} with ${lock}")
        }
        lock.write {
            age = 105
        }

        return age
    }
}
