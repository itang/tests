package demo

import org.fusesource.mqtt.client.MQTT
import org.fusesource.mqtt.client.QoS
import java.net.URISyntaxException


class Client1 {
    object static {
        val CONNECTION_STRING = "tcp://localhost:1883";
        val CLEAN_START = true;
        val KEEP_ALIVE: Short = 30;// 低耗网络，但是又需要及时获取数据，心跳30s

        val RECONNECTION_ATTEMPT_MAX: Long = 6;
        val RECONNECTION_DELAY = 2000L;

        val SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    }

    fun start() {
        val mqtt = MQTT()
        try {
            //设置服务端的ip
            mqtt.setHost(static.CONNECTION_STRING)
            //连接前清空会话信息
            mqtt.setCleanSession(static.CLEAN_START)
            //设置重新连接的次数
            mqtt.setReconnectAttemptsMax(static.RECONNECTION_ATTEMPT_MAX)
            //设置重连的间隔时间
            mqtt.setReconnectDelay(static.RECONNECTION_DELAY)
            //设置心跳时间
            mqtt.setKeepAlive(static.KEEP_ALIVE)
            //设置缓冲的大小
            mqtt.setSendBufferSize(static.SEND_BUFFER_SIZE)

            //创建连接 ,使用阻塞式
            val connection = mqtt.blockingConnection()
            //开始连接
            connection.connect()
            try {
                val start = System.currentTimeMillis()

                var count = 0;
                while (count < 10000) {
                    count++;
                    //订阅的主题
                    val topic = "mqtt/test";
                    //主题的内容
                    val message = "hello " + count + " mqtt!";
                    connection.publish(topic, message.toByteArray(), QoS.AT_LEAST_ONCE, false);
                    //System.out.println("MQTTServer Message  Topic=" + topic + "  Content :" + message);
                    //Thread.sleep(100);
                }

                val end = System.currentTimeMillis()
                System.out.println(">> cost time: ${end - start}ms")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace();
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
}

fun main(args: Array<String>) {
    println("client1...")
    Client1().start()
}
