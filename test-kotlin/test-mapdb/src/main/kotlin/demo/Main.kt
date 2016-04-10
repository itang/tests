package demo

import org.mapdb.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentMap
import kotlin.test.*;

fun main(args: Array<String>) {
    DBMaker.memoryDB().make().use { db ->
        val map = db.hashMap("map").make() as ConcurrentMap<String, String>
        map.put("msg", "hello")

        map["msg"].let {
            println(it)
            assertEquals(it, "hello")
        }
    }

    DBMaker.fileDB("file.db").make().use { db ->
        val map: ConcurrentMap<String, String> = db.hashMap("map", Serializer.STRING, Serializer.STRING).make()

        println("before set:${map["msg"]}")

        val newValue = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        map["msg"] = newValue

        map["msg"].let {
            println("after set: $it")
            assertEquals(newValue, it)
        }
    }
}
