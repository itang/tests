package demo

import org.fusesource.mqtt.client.*
import java.net.URISyntaxException

private class Client2 {
   companion  object static {
        val CONNECTION_STRING = "tcp://localhost:1883"
        val CLEAN_START = false;
        val KEEP_ALIVE: Short = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
        //创建相关的MQTT 的主题列表
        val topics = arrayOf(Topic("mqtt/test", QoS.AT_LEAST_ONCE))


        val RECONNECTION_ATTEMPT_MAX: Long = 6;
        val RECONNECTION_DELAY: Long = 2000;

        val SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    }

    private fun getMQTT(): MQTT {
        return MQTT().apply {
            //设置mqtt broker的ip和端口
            setHost(CONNECTION_STRING)

            //连接前清空会话信息
            setCleanSession(CLEAN_START)

            //Use to set the client Id of the session. This is what an MQTT server uses to identify a session where setCleanSession(false);
            // is being used. The id must be 23 characters or less.
            // Defaults to auto generated id (based on your socket address, port and timestamp)
            setClientId("2")

            //设置重新连接的次数
            setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX)

            //设置重连的间隔时间
            setReconnectDelay(RECONNECTION_DELAY)

            //设置心跳时间
            setKeepAlive(KEEP_ALIVE)

            //设置缓冲的大小
            setSendBufferSize(SEND_BUFFER_SIZE)

            // If set the server will publish the client's Will message to the specified topics if the client has an unexpected disconnection.
            setWillTopic("mqtt/disconnect")

            // The Will message to send. Defaults to a zero length message.
            setWillMessage("clientId-2")

            // Sets the quality of service to use for the Will message. Defaults to QoS.AT_MOST_ONCE.
            setWillQos(QoS.AT_LEAST_ONCE)

            // Set to "3.1.1" to use MQTT version 3.1.1. Otherwise defaults to the 3.1 protocol version.
            //setVersion("3.1.1")
        }
    }

    fun start() {
        //创建MQTT对象
        val mqtt = getMQTT()
        var connection: BlockingConnection? = null;
        try {
            //获取mqtt的连接对象BlockingConnection
            connection = mqtt.blockingConnection()
            //MQTT连接的创建
            connection.connect()

            //订阅相关的主题信息
            val _qoses: ByteArray = connection.subscribe(static.topics)
            //
            while (true) {
                //接收订阅的消息内容
                val message: Message = connection.receive()
                //获取订阅的消息内容
                val payload: ByteArray = message.payload

                // process the message then:
                println("MQTTClient Message  Topic: ${message.topic} Content: ${String(payload)}")

                //签收消息的回执
                message.ack()
                //Thread.sleep(2000)
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace();
        } catch (e: Exception) {
            e.printStackTrace();
        } finally {
            try {
                connection?.disconnect()
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
    }
}

fun main(args: Array<String>) {
    Client2().start()
}