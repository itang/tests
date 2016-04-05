package demo

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent
import io.netty.util.CharsetUtil
import java.util.concurrent.ConcurrentHashMap

fun ByteBuf.asString(): String {
    val bs = ByteArray(this.readableBytes())
    this.readBytes(bs)
    return String(bs)
}

object Global {
    val map = ConcurrentHashMap<ChannelHandlerContext, ChannelHandlerContext> ()//mutableMapOf<ChannelHandlerContext, ChannelHandlerContext> ()
}

class HeartbeatServerHandler : ChannelHandlerAdapter() {

    // Return a unreleasable view on the given ByteBuf
    // which will just ignore release and retain calls.
    companion object {
        private val HEARTBEAT_SEQUENCE: ByteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8));  // 1
        private val HELLO_REPLY_SEQUENCE: ByteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("World", CharsetUtil.UTF_8));
        private val JOIN_MESSAGE: ByteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Client Join", CharsetUtil.UTF_8));  // 1
    }

    override fun channelRegistered(ctx: ChannelHandlerContext?) {
        println(">> channelRegistered:${ctx?.channel()?.remoteAddress()}: ${ctx}")

        Global.map.values.forEach { it.writeAndFlush(JOIN_MESSAGE.duplicate()) }

        Global.map.put(ctx!!, ctx)

        super.channelRegistered(ctx)
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext?) {
        println(">> channelUnregistered:${ctx?.channel()?.remoteAddress()}: ${ctx}")

        Global.map.remove(ctx!!)

        super.channelUnregistered(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        println(">> channelUnregistered:${ctx?.channel()?.remoteAddress()}: ${cause?.message}: ${ctx}")
        super.exceptionCaught(ctx, cause)
    }

    // 流式读.
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println(">> channelRead:${ctx?.channel()?.remoteAddress()}: ${ctx}")
        if (msg is ByteBuf) {
            val d = msg.duplicate()
            val s: String = msg.asString().trim()
            println("msg:'$s'")

            when {
                s == "hello" -> {
                    println("say hello")
                    ctx?.writeAndFlush(HELLO_REPLY_SEQUENCE.duplicate())
                }
                s == "exit" -> ctx?.channel()?.close()
                !s.isBlank() -> {
                    //echo
                    ctx?.writeAndFlush(d)
                }
                else -> println("blank msg.")
            }
        } else {
            super.channelRead(ctx, msg)
        }
    }

    override fun userEventTriggered(ctx: ChannelHandlerContext, event: Any) {
        println(">> userEventTriggered:${ctx?.channel()?.remoteAddress()}: ${ctx}")
        // 当前管道的空闲状态事件?
        if (event is IdleStateEvent) {
            var type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle"
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle"
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle"
            }

            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE) // 3 出错关闭连接

            println("${ctx.channel().remoteAddress()} 超时类型：${type}")
        } else {
            super.userEventTriggered(ctx, event)
        }
    }
}
