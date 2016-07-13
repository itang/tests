package demo

import org.rapidoid.lambda.OneParamLambda
import org.rapidoid.setup.On

fun main(args: Array<String>) {
    On.get("/size").json(object : OneParamLambda<Int, String> {
        override fun execute(msg: String): Int {
            return msg.length
        }
    })
}
