
import java.util.concurrent.CountDownLatch
import scala.concurrent.duration._
import scala.language.postfixOps
import rx.lang.scala.Observable
import rx.lang.scala.Subscriber

object Stopwatch extends App {

  val count = 10
  val latch = new CountDownLatch(count)

  //  Observable.interval(1 second) subscribe { value ⇒
  //    println(value + 1)
  //
  //    latch.countDown()
  //  }

  my_interval(1 second).take(count) subscribe { value ⇒
    println(value)
    latch.countDown()
  }

  latch.await()

  def my_interval(d: Duration): Observable[Long] = {
    Observable({ sub: Subscriber[Long] ⇒
      val t = new Thread() {
        override def run() {
          var i = 1L;
          while (!sub.isUnsubscribed) {
            sub.onNext(i)
            i = i + 1
            Thread.sleep(1000)
          }
        }
      }
      t.setDaemon(true)
      t.start()
    })
  }
}
