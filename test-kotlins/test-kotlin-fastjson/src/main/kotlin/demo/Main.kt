package demo

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

data class KV(var key: String? = null, var value: String? = null)

inline operator fun String.times(i: Int): String {
    return this.repeat(i)
}

fun tryIt(action: () -> Unit) {
    try {
        action()
    } catch(e: Exception) {
        println("WARN: ${e.message}")
    }
}

class Test {
    fun foo1(): List<String> {
        return listOf("helo")
    }

    fun foo2(): MutableList<String> {
        return mutableListOf("hello")
    }

    fun alist1(ls: List<KV>) {

    }

    fun alist2(ls: MutableList<KV>) {

    }
}

fun main(args: Array<String>) {
    val list = listOf(KV("itang"), KV("tqibm", "hello"))

    val jsonString = JSON.toJSONString(list);
    println(jsonString)

    val list1 = JSON.parseObject(jsonString, object : TypeReference<java.util.List<KV>>() {})
    val list2 = JSON.parseObject(jsonString, object : TypeReference<MutableList<KV>>() {})
    val list3 = JSON.parseObject(jsonString, object : TypeReference<List<KV>>() {}) // fastjson 识别类型出错
    val its = listOf(list1, list2, list3)

    Test().alist1(list1 as List<KV>)
    Test().alist2(list1 as MutableList<KV>)

    Test().alist1(list2)
    Test().alist2(list2)

    Test().alist1(list3)
    Test().alist2(list3 as MutableList<KV>)

    for (kvs in its) {
        tryIt {
            println("kvs.javaClass: ${kvs.javaClass}")

            kvs.first().let {
                println(it.javaClass)
            }

            val kListKV: List<KV> = kvs.toList()

            println("kListKV: ${kListKV} , class: ${kListKV.javaClass}")

            println("=" * 100)
        }
    }
}

