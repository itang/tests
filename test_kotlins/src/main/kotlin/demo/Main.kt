package demo

import test.Car
import test.DriveException
import test.SubDriveException
import test.UnknownException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Proxy
import java.lang.reflect.UndeclaredThrowableException

class Bus : Car {

    @Throws(SubDriveException::class, UnknownException::class) //Kotlin未对异常做编译时检查
    override fun drive(people: Int) {
        println(">>> bus drive...")
        if (people > 5) {
            throw UnknownException() // 抛出了基类未声明的受检异常， 动态代理模式下：　UndeclaredThrowableException
        } else {
            throw SubDriveException()
        }
    }
}

fun newBusInvocationHandler(bus: Bus): InvocationHandler {
    return InvocationHandler { _ /* proxy*/, method, args ->
        println("start...")

        try {
            val ret = if (args == null) {
                method?.invoke(bus) // InvocationTargetException
            } else {
                method?.invoke(bus, *args) //InvocationTargetException
            }
            println("end")
            return@InvocationHandler ret
        } catch (e: InvocationTargetException) {
            println("warn e: InvocationTargetException")
            println("e.cause: ${e.cause}")
            throw e.targetException //
        }
    }
}

fun main(args: Array<String>) {
    val stub = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader, arrayOf(Car::class.java), newBusInvocationHandler(Bus())) as Car

    try {
        stub.drive(5)
    } catch (e: SubDriveException) {
        println("ERROR: SubDriveException: $e")
    } catch (e: DriveException) {
        println("ERROR: DriveException: $e")
    } catch(e: UnknownException) {
        println("UnknownException: $e")
    }

    println("*".repeat(100))

    Thread.sleep(100)

    try {
        stub.drive(5)
    } catch(e: Exception) {
        println("Exception: $e")

        assert((e is SubDriveException))
    }

    println("*".repeat(100))

    Thread.sleep(100)

    try {
        stub.drive(6)
    } catch (e: SubDriveException) {
        println("ERROR: SubDriveException: $e")
    } catch (e: DriveException) {
        println("ERROR: DriveException: $e")
    } catch(e: UnknownException) {
        println("UnknownException: $e")
    } catch(e: Exception) {
        println("Exception: $e")
        println("Exception: ${e.cause}")
        assert((e is UndeclaredThrowableException))
        assert(e.cause is UnknownException)
    }
}
