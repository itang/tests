package demo

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import java.util.concurrent.TimeUnit

sealed class Message {
    object Start : Message()

    object Ping : Message()

    object Pong : Message()
}

class PingActor(val pong: ActorRef) : UntypedActor() {
    override fun onReceive(message: Any?) {
        when (message) {
            Message.Start -> {
                pong.tell(Message.Ping, self)
            }
            Message.Pong -> {
                println("${Thread.currentThread()}: pong")
                Thread.sleep(1000)
                pong.tell(Message.Ping, self)
            }
            else -> {
                unhandled(message)
            }
        }
    }
}

class PongActor : UntypedActor() {
    override fun onReceive(message: Any?) {
        when (message) {
            Message.Ping -> {
                println("${Thread.currentThread()}: ping")
                Thread.sleep(1000)
                sender.tell(Message.Pong, self)
            }
            else -> {
                unhandled(message)
            }
        }
    }
}

fun main(args: Array<String>) {
    val system = ActorSystem.create("hello-system")
    val pong = system.actorOf(Props.create(PongActor::class.java))
    val ping = system.actorOf(Props.create({ PingActor(pong) }))

    ping.tell(Message.Start, ActorRef.noSender())
}

