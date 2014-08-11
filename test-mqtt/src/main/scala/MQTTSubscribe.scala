import com.alibaba.fastjson.JSON
import org.fusesource.hawtbuf.{ UTF8Buffer, Buffer }
import org.fusesource.mqtt.client._

object MQTTSubscribe {

  def main(args: Array[String]): Unit = {
    implicit val mqtt = new MQTT()
    mqtt.setHost("localhost", 1883)
    withConnection
  }

  def withConnection(implicit mqtt: MQTT) {
    println("start")
    val c = mqtt.callbackConnection()
    c.listener(new Listener() {

      def onDisconnected() {
        println("onDisconnected")
      }

      def onConnected() {
        println("onConnected")
      }

      def onPublish(topic: UTF8Buffer, payload: Buffer, ack: Runnable) {
        // You can now process a received message from a topic.
        // Once process execute the ack runnable.
        println("onPublish")
        println(payload.utf8().toString)
        ack.run()
      }

      def onFailure(value: Throwable) {
        println(value)
        // c.close(null); // a connection failure occured.
      }
    })

    val callback: Callback[Void] = new Callback[Void]() {
      val topics = Array(new Topic("foo", QoS.AT_LEAST_ONCE));

      override def onSuccess(value: Void): Unit = {
        c.subscribe(topics, new Callback[Array[Byte]]() {
          def onSuccess(qoses: Array[Byte]) {

            println("sss")
            println(new String(qoses, "UTF-8"))
          }

          def onFailure(value: Throwable) {
            println(value)
          }
        })
      }

      override def onFailure(value: Throwable): Unit = ???
    }

    c.connect(callback)

    Thread.sleep(10000)
    println("hrere")
  }
}
