package demo

import java.math.BigDecimal


data class OrderItem(val id: Int, val price: Double, val amount: Int) {

}


fun main(args: Array<String>) {
    val items = listOf(OrderItem(1, 10.0, 3), OrderItem(2, 10.0, 1))

    test(items, 5.1)

    val items2 = listOf(OrderItem(1, 10.0, 3), OrderItem(2, 10.0, 4))

    println()
    test(items2, 2.0)


    val items3 = listOf(OrderItem(1, 10.0, 3), OrderItem(2, 10.0, 4), OrderItem(3, 10.0, 2))

    println()
    test(items3, 5.0)

    val items4 = listOf(OrderItem(1, 10.0, 1), OrderItem(2, 10.0, 1), OrderItem(3, 10.0, 1))

    println()
    test(items4, 1.0)

    val items5 = listOf(OrderItem(1, 10.0, 5))

    println()
    test(items5, 1.0)
}


fun test(items: List<OrderItem>, reduceTotal: Double) {
    test1(items, reduceTotal)
    test2(items, reduceTotal)
}


fun test1(items: List<OrderItem>, reduceTotal: Double) {
    val total = items.map { it.amount * it.price }.sum()
    var acc = 0.0

    val it = items.iterator()
    while (it.hasNext()) {
        val (id, price, amount) = it.next()
        val s = amount * price
        val r = s / total
        val rawItemReduce = r * reduceTotal

        val fixedItemReduce = if (!it.hasNext()) { // last
            reduceTotal - acc
        } else {
            fixed1(rawItemReduce)
        }
        acc += fixedItemReduce

        println("$id-reduce: $rawItemReduce (${fixed1(rawItemReduce)}) <-> $fixedItemReduce : (${fixed1(fixedItemReduce)})")
    }
}


fun test2(items: List<OrderItem>, reduceTotal: Double) {
    val total = items.map { it.amount * fixed2(it.price) }.sum()
    val reduceTotal2 = fixed2(reduceTotal)
    var acc = 0L

    val it = items.iterator()
    while (it.hasNext()) {
        val (id, price, amount) = it.next()
        val s = amount * fixed2(price)
        val r = s * 1.0 / total
        val reduce0 = r * reduceTotal2

        val reduce: Long = if (!it.hasNext()) { // last
            reduceTotal2 - acc
        } else {
            Math.round(reduce0)
        }
        acc += reduce

        println("$id-reduce: $reduce0  <-> $reduce")
    }
}

fun fixed1(value: Double): Double {
    return BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

    //http://www.cnblogs.com/xd502djj/archive/2011/07/21/2112683.html
    //return BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble() //public BigDecimal(double val) 将 double 转换为 BigDecimal，后者是 double 的二进制浮点值准确的十进制表示形式。返回的 BigDecimal 的标度是使 (10scale × val)为整数的最小值。此构造方法的结果有一定的不可预知性
}

fun fixed2(value: Double): Long {
    return Math.round(value * 100)
}
