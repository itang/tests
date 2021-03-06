package demo

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

object Config {
    val PORT_KEY = "PORT"

    val port: Int
        get() = getFromEnvOrProp(PORT_KEY)?.toInt() ?: 8000

    private fun getFromEnvOrProp(key: String): String? {
        return System.getenv(key) ?: System.getenv(key)
    }
}

fun main(args: Array<String>) {
    // Configure the server.
    val bossGroup: EventLoopGroup = NioEventLoopGroup(1)
    val workerGroup: EventLoopGroup = NioEventLoopGroup()
    try {
        val b: ServerBootstrap = ServerBootstrap()
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(HeartbeatHandlerInitializer())

        // Start the server.
        val f: ChannelFuture = b.bind(Config.port).sync()

        // Wait until the server socket is closed.
        f.channel().closeFuture().sync()
    } finally {
        // Shut down all event loops to terminate all threads.
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}
