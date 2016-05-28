package demo

import java.util.concurrent.atomic.AtomicInteger

import javax.jms.Message
import javax.jms.MessageConsumer
import javax.jms.MessageListener
import javax.jms.MessageProducer
import javax.jms.Queue
import javax.jms.Session
import javax.jms.TextMessage

import org.apache.activemq.ActiveMQConnection
import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.command.ActiveMQQueue
import org.apache.activemq.command.ActiveMQTopic
import java.time.Duration


object Main {
    fun start() {
        try {
            val session: Session = {
                val factoryA = ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616")
                val conn = factoryA.createConnection().apply { start() }
                conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
            }()

            testConsumers(session)

            testProducer(session)

        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    private fun testConsumers(session: Session) {
        val queue = ActiveMQQueue(virtualTopicConsumerNameA)
        val consumer1: MessageConsumer = session.createConsumer(queue)
        val consumer2 = session.createConsumer(queue)

        val consumer3 = session.createConsumer(ActiveMQQueue(virtualTopicConsumerNameB))

        val aint1 = AtomicInteger(0)

        val listenerA = MessageListener { message ->
            try {
                println("${aint1.incrementAndGet() } => receive from $virtualTopicConsumerNameA : $message")
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
        consumer1.messageListener = listenerA
        consumer2.messageListener = listenerA

        val aint2 = AtomicInteger(0)

        val listenerB = MessageListener { message ->
            try {
                println("${aint2.incrementAndGet() } => receive from $virtualTopicConsumerNameB : $message")
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }

        consumer3.messageListener = listenerB
    }

    private fun testProducer(session: Session) {
        val producer = session.createProducer(ActiveMQTopic(virtualTopicName))
        for (i in 0..10000) {
            val message = session.createTextMessage("$i message")
            producer.send(message)
            println("\n".repeat(3))
            Thread.sleep(2.seconds().toMillis())
        }
    }

    val virtualTopicName = "VirtualTopic.TEST"

    val virtualTopicConsumerNameB: String = "Consumer.B.VirtualTopic.TEST"

    val virtualTopicConsumerNameA: String = "Consumer.A.VirtualTopic.TEST"
}

fun Int.seconds() = Duration.ofSeconds(this.toLong())

fun main(args: Array<String>) {
    Main.start()
}

