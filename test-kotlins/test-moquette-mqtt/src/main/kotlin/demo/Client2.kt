package demo

import org.fusesource.mqtt.client.*
import java.net.URISyntaxException

class Client2 {
    object static {
        val CONNECTION_STRING = "tcp://localhost:1883"
        val CLEAN_START = true;
        val KEEP_ALIVE: Short = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
        //创建相关的MQTT 的主题列表
        val topics = arrayOf(Topic("mqtt/test", QoS.AT_LEAST_ONCE))


        val RECONNECTION_ATTEMPT_MAX: Long = 6;
        val RECONNECTION_DELAY: Long = 2000;

        val SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    }

    fun start() {
        //创建MQTT对象
        val mqtt = MQTT()
        var connection: BlockingConnection? = null;
        try {
            //设置mqtt broker的ip和端口
            mqtt.setHost(static.CONNECTION_STRING);
            //连接前清空会话信息
            mqtt.setCleanSession(static.CLEAN_START);
            //设置重新连接的次数
            mqtt.setReconnectAttemptsMax(static.RECONNECTION_ATTEMPT_MAX)
            //设置重连的间隔时间
            mqtt.setReconnectDelay(static.RECONNECTION_DELAY)
            //设置心跳时间
            mqtt.setKeepAlive(static.KEEP_ALIVE)
            //设置缓冲的大小
            mqtt.setSendBufferSize(static.SEND_BUFFER_SIZE)

            //获取mqtt的连接对象BlockingConnection
            connection = mqtt.blockingConnection()
            //MQTT连接的创建
            connection.connect()

            //订阅相关的主题信息
            val qoses: ByteArray = connection.subscribe(static.topics)
            //
            while (true) {
                //接收订阅的消息内容
                val message: Message = connection.receive()
                //获取订阅的消息内容
                val payload: ByteArray = message.getPayload()
                // process the message then:
                System.out.println("MQTTClient Message  Topic=" + message.getTopic() + " Content :" + String(payload))
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