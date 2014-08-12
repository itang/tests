import java.util.Date

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import org.fusesource.mqtt.client.{ MQTT, BlockingConnection, QoS }
import org.slf4j.LoggerFactory

import scala.beans.BeanProperty

import scatang._

object MQTTPublish extends Base {

  val logger = LoggerFactory.getLogger(this.getClass)

  case class User(@BeanProperty id: Long, @BeanProperty name: String, @BeanProperty date: Date) {
    def this() = this(0, "", null)
  }

  def main(args: Array[String]): Unit = {
    implicit val mqtt = getMQTT()
    withIt { connection =>
      logger.debug(s"connection: ${connection}")
      time {
        for (i <- (1 to 10000)) {
          logger.debug("send message...")
          val content = JSON.toJSONString(User(1, "itang", new Date), SerializerFeature.UseISO8601DateFormat)
          println(content)
          //connection.publish("foo", s"""Hello from Scala ${new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())}""".getBytes("UTF-8"), QoS.AT_LEAST_ONCE, false)
          connection.publish(defaultTopic, content.getBytes("UTF-8"), QoS.AT_LEAST_ONCE, false)
          logger.debug("end send message...")
        }
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
    //mqtt.setClientId(java.util.UUID.randomUUID().toString())
    mqtt
  }
}
