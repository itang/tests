import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.*

class A constructor(val name: String, val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) {
    var age = 10;

    public fun hello(): Int {
        lock.read {
            println("${name}: ${age} with ${lock}")
        }
        lock.write {
            age = 105
        }

        return age
    }
}
