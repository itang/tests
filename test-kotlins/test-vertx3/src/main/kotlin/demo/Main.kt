package demo

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main(args: Array<String>) {
    Vertx.vertx(VertxOptions()).run {
        val options = DeploymentOptions().setWorker(true).setInstances(4)
        deployVerticle(MyFirstVerticle::class.java.name, options)
    }
}
