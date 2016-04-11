package demo

import org.fusesource.hawtbuf.Buffer
import org.fusesource.hawtbuf.UTF8Buffer
import org.fusesource.mqtt.client.*
import java.net.URISyntaxException

class ClientAsync {
    object static {
        val CONNECTION_STRING = "tcp://localhost:1883"
        val CLEAN_START = false;
        val KEEP_ALIVE: Short = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
        //创建相关的MQTT 的主题列表
        val topics = arrayOf(Topic("mqtt/disconnect", QoS.AT_LEAST_ONCE))


        val RECONNECTION_ATTEMPT_MAX: Long = 6;
        val RECONNECTION_DELAY: Long = 2000;

        val SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    }

    private fun getMQTT(): MQTT {
        return MQTT().apply {
            //设置mqtt broker的ip和端口
            setHost(static.CONNECTION_STRING)

            //连接前清空会话信息
            setCleanSession(static.CLEAN_START)

            //Use to set the client Id of the session. This is what an MQTT server uses to identify a session where setCleanSession(false);
            // is being used. The id must be 23 characters or less.
            // Defaults to auto generated id (based on your socket address, port and timestamp)
            setClientId("3")

            //设置重新连接的次数
            setReconnectAttemptsMax(static.RECONNECTION_ATTEMPT_MAX)

            //设置重连的间隔时间
            setReconnectDelay(static.RECONNECTION_DELAY)

            //设置心跳时间
            setKeepAlive(static.KEEP_ALIVE)

            //设置缓冲的大小
            setSendBufferSize(static.SEND_BUFFER_SIZE)
        }
    }

    fun start() {
        //创建MQTT对象
        val mqtt = getMQTT()
        val connection: CallbackConnection = mqtt.callbackConnection()
        val result = Promise<Throwable>()

        connection.listener(object : Listener {
            override fun onDisconnected() {
                println("connnect listerner onDisconnected")
            }

            override fun onConnected() {
                println("connnect listerner onConnected")
            }

            override fun onPublish(topic: UTF8Buffer, payload: Buffer, ack: Runnable) {
                // You can now process a received message from a topic.
                // Once process execute the ack runnable.
                println("receive ${topic.toString()}, payload: ${payload.utf8().toString()}")

                ack.run()
            }

            override fun onFailure(value: Throwable) {
                println("connnect listerner onFailure")
                //connection.close(null); // a connection failure occured.
            }
        })

        connection.connect(object : Callback<Void?> {
            override fun onFailure(value: Throwable) {
                println(" connection.connect onFailure")
                //result.failure(value); // If we could not connect to the server.
            }

            // Once we connect..
            override fun onSuccess(v: Void?) {
                println("connection.connect onSuccess")

                // Subscribe to a topic
                val topics = arrayOf(Topic("foo", QoS.AT_LEAST_ONCE), Topic("mqtt/test", QoS.AT_LEAST_ONCE))
                connection.subscribe(topics, object : Callback<ByteArray> {
                    override fun onSuccess(qoses: ByteArray) {
                        // The result of the subcribe request.
                    }

                    override fun onFailure(value: Throwable) {
                        //connection.close(null); // subscribe failed.
                    }
                });

                // Send a message to a topic
                println("try publish message to foo topic...")
                connection.publish("foo", "Hello".toByteArray(), QoS.AT_LEAST_ONCE, false, object : Callback<Void> {
                    override fun onSuccess(v: Void?) {
                        println("connection.publish onSuccess")
                        // the pubish operation completed successfully.
                    }

                    override fun onFailure(value: Throwable) {
                        println("connection.publish onFailure")
                        //connection.close(null); // publish failed.
                    }
                });

                // To disconnect..
//                connection.disconnect(object : Callback<Void> {
//                    override fun onSuccess(v: Void) {
//                        // called once the connection is disconnected.
//                    }
//
//                    override fun onFailure(value: Throwable) {
//                        // Disconnects never fail.
//                    }
//                });
            }
        })

        result.await()
    }
}

fun main(args: Array<String>) {
    ClientAsync().start()
}