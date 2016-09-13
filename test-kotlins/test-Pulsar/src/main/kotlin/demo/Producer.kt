@file:JvmName("Producer")

package demo

import com.yahoo.pulsar.client.api.PulsarClient


class HelloProducer {
    fun run() {
        val client = PulsarClient.create("http://localhost:8080")
        val producer = client.createProducer("persistent://sample/standalone/ns1/my-topic")

        // Publish 10 messages to the topic
        for (i in 0..9) {
            producer.send("my-message $i".toByteArray())
        }

        client.close()
    }
}

fun main(args: Array<String>) {
    HelloProducer().run()
}
