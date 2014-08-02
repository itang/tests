package example

import java.util.Date

import scala.beans.BeanProperty

import org.fusesource.mqtt.client.BlockingConnection
import org.fusesource.mqtt.client.MQTT
import org.fusesource.mqtt.client.QoS
import org.slf4j.LoggerFactory

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature

object Example {

  def echo(s: String) = s

  val logger = LoggerFactory.getLogger(this.getClass)

  case class User(@BeanProperty id: Long, @BeanProperty name: String, @BeanProperty date: Date)

  def main(args: Array[String]): Unit = {
    implicit val mqtt = getMQTT()
    withIt { connection =>
      logger.debug(s"connection: ${connection}")

      for (i <- (1 to 100)) {
        logger.debug("send message...")
        val content = JSON.toJSONString(User(1, "itang", new Date), SerializerFeature.UseISO8601DateFormat)
        println(content)
        //connection.publish("foo", s"""Hello from Scala ${new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())}""".getBytes("UTF-8"), QoS.AT_LEAST_ONCE, false)
        connection.publish("foo", content.getBytes("UTF-8"), QoS.AT_LEAST_ONCE, false)
        logger.debug("end send message...")
        Thread.sleep(100)

        //      withIt { c2 =>
        //        val topics = List(new Topic("foo", QoS.AT_LEAST_ONCE))
        //        val qosec = c2.subscribe(topics.toArray)
        //
        //        val message = c2.receive()
        //        println("topic:" + message.getTopic())
        //        val payload = message.getPayload()
        //        println("receive:" + new String(payload, "UTF-8"))
        //
        //        // process the message then:
        //        message.ack();
        //      }
      }
    }
  }

  def withIt(f: BlockingConnection => Unit)(implicit mqtt: MQTT) {
    val connection = mqtt.blockingConnection()
    connection.connect()

    f(connection)

    connection.disconnect()
  }

  def getMQTT(): MQTT = {
    val mqtt = new MQTT()

    mqtt.setHost("localhost", 1883)
    mqtt.setCleanSession(false)
    mqtt.setClientId(java.util.UUID.randomUUID().toString())
    mqtt
  }
}

