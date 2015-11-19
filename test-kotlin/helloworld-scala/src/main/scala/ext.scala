package demo

import java.util.concurrent.locks.ReentrantReadWriteLock

package object concurrent {
  implicit class WRL(lock: ReentrantReadWriteLock) {
    def write[T](block: => T): T = {
      lock.writeLock.lock
      val ret : T = block
      lock.writeLock.unlock
      ret
    }
  }
}
