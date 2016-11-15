package demo

import io.vertx.core.*
import io.vertx.core.Future.future
import io.vertx.core.buffer.Buffer
//import io.vertx.core.VertxOptions
import io.vertx.core.http.HttpServer
import java.time.Duration
import java.util.concurrent.atomic.AtomicLong
import khttp.get
import io.vertx.core.json.JsonObject


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
