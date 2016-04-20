package demo

import org.fusesource.mqtt.client.MQTT
import org.fusesource.mqtt.client.QoS
import java.net.URISyntaxException


private class Client1 {
    companion object static {
        val CONNECTION_STRING = "tcp://localhost:1883";
        val CLEAN_START = false;
        val KEEP_ALIVE: Short = 30;// 低耗网络，但是又需要及时获取数据，心跳30s

        val RECONNECTION_ATTEMPT_MAX: Long = 6;
        val RECONNECTION_DELAY = 2000L;

        val SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    }

    private fun getMQTT(): MQTT {
        return MQTT().apply {
            //设置服务端的ip
            setHost(CONNECTION_STRING)

            //连接前清空会话信息
            // Set to false if you want the MQTT server to persist topic subscriptions and ack positions across client sessions.
            // Defaults to true.
            setCleanSession(CLEAN_START)

            //Use to set the client Id of the session. This is what an MQTT server uses to identify a session where setCleanSession(false);
            // is being used. The id must be 23 characters or less.
            // Defaults to auto generated id (based on your socket address, port and timestamp)
            setClientId("1")

            //设置重新连接的次数
            //The maximum number of reconnect attempts before an error is reported back to the client after a server connection had previously been established.
            // Set to -1 to use unlimited attempts. Defaults to -1.
            setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX)

            //设置重连的间隔时间
            // How long to wait in ms before the first reconnect attempt. Defaults to 10.
            setReconnectDelay(RECONNECTION_DELAY)

            //设置心跳时间
            setKeepAlive(KEEP_ALIVE)

            //设置缓冲的大小
            setSendBufferSize(SEND_BUFFER_SIZE)
        }
    }

    fun start() {
        val mqtt = MQTT()
        try {
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
                    val message = "hello $count mqtt!";
                    connection.publish(topic, message.toByteArray(), QoS.AT_LEAST_ONCE, false);
                    System.out.println("MQTTServer Message Topic=$topic Content :$message");
                    //Thread.sleep(10);
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
