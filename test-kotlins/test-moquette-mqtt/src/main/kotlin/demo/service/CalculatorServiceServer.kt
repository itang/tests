package demo.service

import com.hileco.drpc.mqtt.MqttDrpcClient
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder


private class CalculatorServiceServer {
    fun start() {
        val client: MqttDrpcClient = MqttDrpcClientBuilder().withQualityOfServiceLevel(1).build("tcp://localhost:1883")
        client.connect()

        println("try publish remote-calculator")
        val c = client.publish(CalculatorService::class.java, "remote-calculator", object : CalculatorService {
            override fun calculate(a: Int, b: Int): Int {
                return a + b
            }
        })
        //c.close()
    }
}

fun main(args: Array<String>) {
    CalculatorServiceServer().start()
}
