package demo

import io.micronaut.context.ApplicationContext
import javax.inject.Singleton

interface Engine {
    fun getCylinders(): Int
    fun start()
}

@Singleton
class V8Engine : Engine {
    override fun getCylinders(): Int {
        return 1
    }

    override fun start() {
        println("V8 start...")
    }
}

@Singleton
class Vehicle /* @Inject //can ignore*/ constructor(val engine: Engine) {
    fun start() {
        engine.start()
    }
}

@Singleton
class MyBean {
    val id = 1L
    override fun toString(): String {
        return "MyBean6(id=$id)"
    }
}

fun main(args: Array<String>) {
    ApplicationContext.run().use { context ->
        val myBean = context.getBean(MyBean::class.java)
        // do something with your bean
        println(myBean)

        val vehicle = context.getBean(Vehicle::class.java)
        vehicle.start()
    }
}
