import java.util.concurrent.TimeUnit
import java.util.{Date, UUID}

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.api.annotation.{REntity, RId}
import org.redisson.config.Config
import util._

import scala.beans.BeanProperty

object Main {

  def main(args: Array[String]): Unit = time {
    val config: Config = new Config()
    //config.setTransportMode(TransportMode.EPOLL)
    config.useSingleServer().setAddress("redis://127.0.0.1:6379")

    val redisson: RedissonClient = Redisson.create(config)
    time {
      val remoteService = redisson.getRemoteService
      remoteService.register(classOf[IHello], new HelloImpl(redisson), 12)

      val hello: IHello = remoteService.get(classOf[IHello])
      val objectId1 = UUID.randomUUID().toString + "_1"
      val objectId2 = UUID.randomUUID().toString + "_2"
      new Thread() {
        override def run(): Unit = hello.say(objectId1)
      }.start()
      hello.say(objectId2)
    }

    val liveObjectService = time {
      redisson.getLiveObjectService
    }
    time {
      val myLiveObject = new MyLiveObject
      myLiveObject.name = "id"
      myLiveObject.value = "hello"
      try {
        liveObjectService.persist[MyLiveObject](myLiveObject)
      } catch {
        case e: Throwable => println(e.getMessage)
      }
    }
    val myLiveObjectP = liveObjectService.get(classOf[MyLiveObject], "id")
    println(myLiveObjectP.getName)
    println(s"myLiveObjectP.value: ${myLiveObjectP.getValue}")
    myLiveObjectP.setValue("hello2")

    val myLiveObjectP2 = liveObjectService.get(classOf[MyLiveObject], "id")
    println(myLiveObjectP2.getName)
    println(s"myLiveObjectP2.value: ${myLiveObjectP2.getValue}")

    myLiveObjectP2.setMyOtherObject(new MyOtherObject)

    val myLiveObjectP3 = liveObjectService.get(classOf[MyLiveObject], "id")
    println(s"myLiveObjectP3.value: ${myLiveObjectP2.getMyOtherObject}")
    val myOtherObject = myLiveObjectP3.getMyOtherObject
    println(myOtherObject.getClass)
    println(myOtherObject.getValue)

    myOtherObject.setValue("my other value2")
    val p4 = liveObjectService.get(classOf[MyLiveObject], "id")
    println(p4.getMyOtherObject.getValue)

    time {
      liveObjectService.registerClass(classOf[MyOtherLiveObject])
    }

    val op = new MyOtherLiveObject
    op.setName("1")
    try {
      liveObjectService.persist(op)
    } catch {
      case e: Exception => println(e.getMessage)
    }
    val op2 = liveObjectService.get(classOf[MyOtherLiveObject], "1")
    println(op2.getName)
    println(op2.getValue())
    op2.setValue("test2")
    val op3 = liveObjectService.get(classOf[MyOtherLiveObject], "1")
    println(op3.getName)
    println(op3.getValue())

    p4.setMyOtherLiveObject(op3)

    time {
      val p5 = liveObjectService.get(classOf[MyLiveObject], "id")
      println(p5.getMyOtherLiveObject.getValue)

      p5.getMyOtherLiveObject.setValue("test3"+UUID.randomUUID().toString)

      val p6 = liveObjectService.get(classOf[MyLiveObject], "id")
      println(p6.getMyOtherLiveObject.getValue)
    }

    redisson.shutdown()
  }
}

@REntity
class MyLiveObject {
  @BeanProperty
  @RId
  var name: String = _

  @BeanProperty
  var value: String = _

  @BeanProperty
  var myOtherObject: MyOtherObject = _

  @BeanProperty
  var myOtherLiveObject: MyOtherLiveObject = _
}

class MyOtherObject extends Serializable {
  @BeanProperty
  var value: String = "other value"
}

@REntity
class MyOtherLiveObject {
  @BeanProperty
  @RId
  var name: String = _
  @BeanProperty
  var value: String = "MyOtherLiveObject"
}

trait IHello {
  def say(objectId: String): Unit
}

class HelloImpl(val redisson: RedissonClient) extends IHello {
  override def say(objectId: String): Unit = {
    println(s"say for ${objectId} ${Thread.currentThread()} ...")
    val lock = redisson.getLock(objectId)
    lock.lock(10, TimeUnit.SECONDS) // Acquire lock and release it automatically after 10 seconds if unlock method hasn't been invoked
    println(s"locked. ${objectId}")
    try {
      //Thread.sleep(2 * 100)
      println(s"${objectId} do something, say hello ${new Date()}")
    } catch {
      case e: Throwable => e.printStackTrace()
    } finally {
      lock.unlock()
      println(s"unlocked. ${objectId}")
    }
  }
}
