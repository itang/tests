package demo

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.kotlin.coroutines.awaitResult
import io.vertx.redis.RedisClient
import kotlinx.coroutines.experimental.runBlocking
import io.vertx.redis.RedisOptions


fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    try {
        runBlocking {
            val ret: String = awaitResult<String> { h ->
                httpClient(vertx).apply {
                    postAbs("http://139.199.4.119/api/ping").`as`(BodyCodec.string("UTF-8"))
                            .send(object : Handler<AsyncResult<HttpResponse<String>>> {
                                override fun handle(event: AsyncResult<HttpResponse<String>>) {
                                    h.handle(event.map { it.body() })
                                }
                            })
                }.close()
            }
            println(ret)

            val service = Service(httpClient(vertx));

            val logs: Result = awaitResult { h -> service.logs(h) }
            logs.data?.forEachIndexed { i, it ->
                println("$i:${it.from}: ${it.to}")
            }

            val logs2: Result = service.logs2()
            println(logs2)
        }

        //test redis transaction
        runBlocking {
            try {
                val redisClient = redisClient(vertx)
                val t = redisClient.transaction()

                val ret = awaitResult<JsonArray> { h ->
                    t.multi { event ->
                        t.set("name", "itang") { e1 -> println(e1) }
                        t.incr("age") { e2 -> println(e2) }
                        t.expire("age", 1000) { e3 -> println(e3) }
                        t.exec(h)
                    }
                }
                println(ret)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } finally {
        vertx.close()
    }
}
/*
 transaction.multi(reply -> {
      assertTrue(reply.succeeded());
      transaction.set("multi-key", "first", reply2 -> {
        assertTrue(reply2.succeeded());
        transaction.set("multi-key2", "second", reply3 -> assertTrue(reply3.succeeded()));
        transaction.get("multi-key", reply4 -> {
          assertTrue(reply4.succeeded());
          assertTrue("QUEUED".equalsIgnoreCase(reply4.result()));
        });
        transaction.exec(reply5 -> {
          assertTrue(reply5.succeeded());
          testComplete();
        });
      });
    });
 */

class Service(private val client: WebClient) {
    fun logs(h: Handler<AsyncResult<Result>>) {
        client.getAbs("http://139.199.4.119/api/dict/logs")
                .`as`(BodyCodec.json(Result::class.java))
                .send { event -> h.handle(event.map { it.body() }) }
    }

    suspend fun logs2(): Result = awaitResult { h -> logs(h) }
}

data class Result(var ok: Boolean? = null,
                  var code: Int? = null,
                  var status: Int? = null,
                  var message: String? = null,
                  var data: List<Log>? = null,
                  var errors: Any? = "")

data class Log(var from: String? = null,
               var to: String? = null,
               var fromLang: String? = null,
               var toLang: String? = null,
               var id: Long? = null,
               var updated_at: Long? = null,
               var created_at: Long? = null)

fun httpClient(vertx: Vertx): WebClient {
    val opts = WebClientOptions()
    return WebClient.create(vertx, opts)
}

fun redisClient(vertx: Vertx): RedisClient {
    val config = RedisOptions()
            .setHost("127.0.0.1")

    return RedisClient.create(vertx, config)
}