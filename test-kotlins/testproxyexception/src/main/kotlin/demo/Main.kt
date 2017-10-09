package demo

import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy

fun main(args: Array<String>) {
//    try {
//        val p: Phone = MyPhone()
//
//        p.call("119")
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }

    val handler = object : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
            val phone = MyPhone()
            return if (args != null) {
                method.invoke(phone, *args)
            } else {
                method.invoke(phone)
            }
        }
    }

    val handler2 = object : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
            val phone = MyPhone()

            try {
                return if (args != null) {
                    method.invoke(phone, *args) //如果反射调用出现错误会抛 InvocationTargetException
                } else {
                    method.invoke(phone)
                }
            } catch (e: Exception) {
                println("eee")
                when (e) {
                    is InvocationTargetException -> { // 获取underlying 异常
                        println("InvocationTargetException...")
                        throw e.cause!!
                    }
                    else -> {
                        println("other exception")
                        println(e.javaClass)
                        throw e
                    }
                }
            }
        }
    }

    val pp: Phone = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader, arrayOf(Phone::class.java), handler) as Phone
    try {
        pp.call("119") //如果代理实例调用出现接口未声明的受检异调用， 抛出:  UndeclaredThrowableException
    } catch (e: Exception) {
        println(e.javaClass) // UndeclaredThrowableException <- InvocationTargetException

        Thread.sleep(10)
        e.printStackTrace()
    }
    Thread.sleep(500)
    val pp2: Phone = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader, arrayOf(Phone::class.java), handler2) as Phone
    try {
        pp2.call("119")
    } catch (e: Exception) {
        println(e.javaClass) // class demo.ErrorNumber

        Thread.sleep(10)
        e.printStackTrace()
    }
}
