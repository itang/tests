@file:JvmName("Consumer")

package demo

import com.yahoo.pulsar.client.api.ConsumerConfiguration
import com.yahoo.pulsar.client.api.Message
import com.yahoo.pulsar.client.api.PulsarClient
import com.yahoo.pulsar.client.api.SubscriptionType

fun Message.dataAsString(): String {
    return String(this.data, charset = Charsets.UTF_8)
}

class HelloConsumber {
    fun run() {
        val client = PulsarClient.create("http://localhost:8080")

        val consumer = client.subscribe("persistent://sample/standalone/ns1/my-topic", "my-subscribtion-name", ConsumerConfiguration().apply {
            this.subscriptionType = SubscriptionType.Shared // load-balance
        })
        while (true) {
            val message = consumer.receive()
            println("Received message: ${message.dataAsString()}")

            consumer.acknowledge(message)
        }
    }
}

fun main(args: Array<String>) {
    HelloConsumber().run()
}
