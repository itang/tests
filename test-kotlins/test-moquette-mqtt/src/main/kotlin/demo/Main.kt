package demo

import io.moquette.server.Server
import org.slf4j.LoggerFactory

// @see http://blog.csdn.net/zhu_tianwei/article/details/42983867
class MainServer {
    object static {
        val logger = LoggerFactory.getLogger(MainServer::class.java)

        val moquetteStoreFile = "moquette_store.mapdb"
    }

    val server : Server = Server()

    init {
        server.startServer()
        println("server started.")
        Runtime.getRuntime().addShutdownHook(object:Thread() {
            override fun run() {
                println("Try stop server...")
                server.stopServer()
            }
        })
    }
}

fun main(args: Array<String>) {
    val mainServer = MainServer()
}

