import com.alibaba.fastjson.JSON
import org.fusesource.hawtbuf.{ UTF8Buffer, Buffer }
import org.fusesource.mqtt.client._

object MQTTSubscribe extends Base {

  def main(args: Array[String]): Unit = {
    implicit val mqtt = new MQTT()
    mqtt.setHost("localhost", 1883)

    dosubscribe
  }

  def dosubscribe(implicit mqtt: MQTT) {
    println("start")
    val c = mqtt.callbackConnection()
    c.subscribe(defaultTopic) { (topic, payload, ack) =>
      println("Received: " + payload.utf8().toString)
      ack.run()
    }

    //Thread.sleep(100000)
    println("exit")
  }

  implicit class CallbackConnectionWrapper(cc: CallbackConnection) {
    def subscribe(topic: String)(block: (UTF8Buffer, Buffer, Runnable) => Unit) {
      cc.listener(new Listener() {

        def onDisconnected() {
          println("onDisconnected")
        }

        def onConnected() {
          println("onConnected")
        }

        def onPublish(topic: UTF8Buffer, payload: Buffer, ack: Runnable) {
          // You can now process a received message from a topic.
          // Once process execute the ack runnable.
          block(topic, payload, ack)
        }

        def onFailure(value: Throwable) {
          println("listener onFailure")
          // c.close(null); // a connection failure occured.
        }
      })

      val callback: Callback[Void] = new Callback[Void]() {
        val topics = Array(new Topic(topic, QoS.AT_LEAST_ONCE));

        override def onSuccess(value: Void): Unit = {
          cc.subscribe(topics, new Callback[Array[Byte]]() {
            def onSuccess(qoses: Array[Byte]) {

              println("subscribe onSuccess")
              println(new String(qoses, "UTF-8"))
            }

            def onFailure(value: Throwable) {
              println("subscribe onFailure")
            }
          })
        }

        override def onFailure(value: Throwable): Unit = ???
      }

      cc.connect(callback)
    }
  }
}
