package demo

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.handler.timeout.IdleStateHandler
import java.util.concurrent.TimeUnit

class HeartbeatHandlerInitializer : ChannelInitializer<Channel>() {
    companion object {
        val READ_IDEL_TIME_OUT = 30L // 读超时
        val WRITE_IDEL_TIME_OUT = 40L // 写超时
        val ALL_IDEL_TIME_OUT = 60L // 所有超时
    }

    override protected fun initChannel(ch: Channel): Unit {
        val pipeline: ChannelPipeline = ch.pipeline()
        pipeline.addLast(IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS))// 1
        pipeline.addLast(HeartbeatServerHandler())// 2
    }
}
