package demo.service

import com.hileco.drpc.mqtt.MqttDrpcClient
import com.hileco.drpc.mqtt.MqttDrpcClientBuilder

private class CalculatorServiceClient {
    fun start() {
        val client: MqttDrpcClient = MqttDrpcClientBuilder().build("tcp://localhost:1883")
        client.connect()

        println("try invoke remote-calculator")

        val collector = client.connector(CalculatorService::class.java)
        //        val c = collector.drpc(
        //                { d -> d.calculate(1, 2) },
        //                { r -> System.out.println("CalculatorService#calculate(1,2) = " + r) }
        //        )
        //c.close()
        val remoteCalculator = collector.connect("remote-calculator") // connect to the calculator identified by the id we've registered with at the service side

        for(i in 0..100) {
            println(remoteCalculator.calculate(1, i))
        }
    }
}

fun main(args: Array<String>) {
    CalculatorServiceClient().start()
}
