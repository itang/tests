package demo

import io.vertx.core.*
import io.vertx.core.Future.future
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
//import io.vertx.core.VertxOptions
import io.vertx.core.http.HttpServer
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong
import khttp.get
import io.vertx.core.json.JsonObject
import java.text.SimpleDateFormat
import java.util.*
import io.vertx.core.net.NetServerOptions
import java.nio.charset.Charset


object GlobalState {
    val count = AtomicLong()
}

object GlobalConfig {
    val PORT = 8080
}

class HelloVerticle : AbstractVerticle() {
    override fun start() {
        println("${this.javaClass.simpleName} start...")
        println("hello ${System.getenv("USER")}")
    }

    override fun stop() {
        println("${this.javaClass.simpleName} stop...")
    }
}

class WorkerVerticle : AbstractVerticle() {
    override fun start() {
        println("${this.javaClass.simpleName} ${System.identityHashCode(this)} start...")
        println("config: ${this.config()}")

        val context = vertx.orCreateContext
        println("""
context.isEventLoopContext: ${context.isEventLoopContext}
context.isWorkerContext: ${context.isWorkerContext}
context.isMultiThreadedWorkerContext: ${context.isMultiThreadedWorkerContext}
!Context.isOnVertxThread(): ${!Context.isOnVertxThread()}""")

        2.loop {
            vertx.orCreateContext.runOnContext { v ->
                println("This will be executed asynchronously in the same context, ${Thread.currentThread()}")
            }
        }

        vertx.setTimer(1000) { id ->
            println("And one second later this is printed: ${Thread.currentThread()}")
        }
        println("first this is printed")

        var tc = 0
        var timerID: Long = 0
        timerID = vertx.setPeriodic(1000) { id ->
            println("And every second this is printed: $id")
            if (tc > 2) {
                vertx.cancelTimer(timerID)
            }
            tc++
        }
    }
}

class EventBusConsumberTestVecticle : AbstractVerticle() {
    override fun start() {
        val eventBus = vertx.eventBus()
        val consumer: MessageConsumer<String> = eventBus.consumer("news.uk.sport")
        consumer.handler { message ->
            println("I have received  a message1: ${message.body()}")
        }
        consumer.completionHandler { rs ->
            if (rs.succeeded()) {
                System.out.println("The handler registration has reached all nodes")
            } else {
                System.out.println("Registration failed!")
            }
        }
        eventBus.consumer("news.uk.sport") { message: Message<String> ->
            System.out.println("I have received a message2: " + message.body())
            if (message.headers().contains("date")) {
                println(">> ${message.headers().getAll("date")}")
                message.reply("date is ${message.headers()["date"]}")
            }
        }//consumer2

        eventBus.consumer("news.uk.ent") { message: Message<String> ->
            System.out.println("I have received a message3: " + message.body())
            message.reply("回复1")
        }//consumer3
    }
}

class EventBusPublisherTestVecticle : AbstractVerticle() {
    override fun start() {
        val eventBus = vertx.eventBus()
        val publisher = eventBus.publisher<String>("news.uk.sport")
        vertx.setPeriodic(2000) { id ->
            publisher.send("football game 1")

            val options = DeliveryOptions().addHeader("date", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
            eventBus.publish("news.uk.sport", "Yay! Someone kicked a ball", options)

            eventBus.send("news.uk.ent", "Yay! Someone kicked a ball", { ar: AsyncResult<Message<String>> ->
                if (ar.succeeded()) {
                    println("Received reply: " + ar.result().body())
                }
            })
        }
    }
}

fun <T : Number> T.loop(action: (Long) -> Unit) {
    var i = 0L
    val max = this.toLong()
    while (i < max) {
        action(i)
        i++
    }
}

class FutureAllVerticle : AbstractVerticle() {
    override fun start() {
        //test all
        val f1 = future<String>()
        val f2 = future<String>()
        val f3 = future<String>()

        f1.setHandler { v ->
            println("f1: ${v.succeeded()}")
        }
        f2.setHandler { v ->
            println("f2: ${v.succeeded()}")
        }
        f3.setHandler { v ->
            println("f3: ${v.succeeded()}")
        }

        f1.complete("hello")
        f2.fail("f2 failed")
        f3.complete("world")
        CompositeFuture.all(listOf(f1, f2, f3)).setHandler { ar ->
            if (ar.succeeded()) {
                println("all successed")
            } else {
                println("at least one faild")
            }
        }
    }
}

class BufferTestVecticle : AbstractVerticle() {
    override fun start() {
        val buffer = Buffer.buffer()
        buffer.setInt(0, 100)
        buffer.setString(4, "hello")
        buffer.setLong(10, 200)
        println("buffer length: ${buffer.length()}")
        println("buffer[0]: ${buffer.getInt(0)}")
        println("buffer[1]: ${buffer.getString(4, 9)}")
    }
}

class TcpServerTestVecticle : AbstractVerticle() {
    override fun start() {
        val options = NetServerOptions().setPort(4321)
        val netServer = vertx.createNetServer(options)
        netServer.connectHandler { socket ->
            socket.handler { buffer ->
                val bytes = buffer.bytes
                val body = String(bytes, Charsets.UTF_8)
                println("received from ${socket.remoteAddress()}: ${body}")

                if (body?.trim() == "exit") {
                    socket.write("bye!")
                    socket.close()
                } else {
                    socket.write(body)
                }
            }
            socket.closeHandler { v ->
                println("socket is closed")
            }
        }
        netServer.listen { ar ->
            if (ar.succeeded()) {
                println("net server started!!")
            }
        }
    }
}

fun main(args: Array<String>) {
    val vertx = Vertx.vertx(/*VertxOptions().setEventLoopPoolSize(4).setWorkerPoolSize(4)*/)

    //
    vertx.deployVerticle("demo.HelloVerticle") // standard verticles
    val config = JsonObject().put("name", "tim").put("directory", "/blah")
    val deployOpts = DeploymentOptions()
            .setWorker(true)
            .setMultiThreaded(true)
            .setInstances(2)
            .setConfig(config)
            .setHa(true)// Multi-threaded worker verticles
    vertx.deployVerticle("demo.WorkerVerticle", deployOpts)

    vertx.deployVerticle("demo.FutureAllVerticle")

    vertx.deployVerticle("demo.EventBusConsumberTestVecticle")

    vertx.deployVerticle("demo.EventBusPublisherTestVecticle")

    vertx.deployVerticle("demo.BufferTestVecticle")

    vertx.deployVerticle(TcpServerTestVecticle())

    vertx.setPeriodic(Duration.ofSeconds(10).toMillis()) { id ->
        println("count ${GlobalState.count.andIncrement}")
    }

    val httpServerFuture = future<HttpServer>()
    vertx.createHttpServer().requestHandler { ctx ->
        println(Thread.currentThread())
        //Thread.sleep(1000 * 20)//warning
        val count = ctx.getParam("count")
        ctx.response().end("Hello, World $count")
    }.listen(GlobalConfig.PORT, httpServerFuture.completer())

    CompositeFuture.all(listOf(httpServerFuture)).setHandler { ar ->
        if (ar.succeeded()) {
            println("http server start success.")
            vertx.setPeriodic(Duration.ofSeconds(15).toMillis()) { id ->
                vertx.executeBlocking({ f: Future<String> ->
                    val ret = get("http://localhost:${GlobalConfig.PORT}?count=${GlobalState.count.get()}").text
                    f.complete(ret)
                }, { res -> println(res.result()) })
            }
        } else {
            println("http server start failed!")
        }
    }


    //test Future compose
    val testFile = "build/test"
    val fs = vertx.fileSystem()
    val startFuture = future<Void>()
    val fut1 = future<Void>()
    try {
        fs.deleteBlocking(testFile)
    } catch (e: Exception) {
        println(e.message)
    }

    fs.createFile(testFile, fut1.completer())
    fut1.compose { v ->
        val fut2 = Future.future<Void>()
        fs.writeFile(testFile, Buffer.buffer().appendString("hello,world ${java.time.LocalDateTime.now()}"), fut2.completer())
        fut2
    }.compose({ v ->
        val fut3 = Future.future<Void>()
        fs.move(testFile, "build/test0", fut3.completer())
    }, startFuture)

    startFuture.setHandler { ar ->
        if (ar.succeeded()) {
            println("一系列操作成功")
        } else {
            println("出错了 ${ar.cause()}")
        }
    }
}
