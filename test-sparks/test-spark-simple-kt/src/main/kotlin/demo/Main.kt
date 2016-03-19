package demo

import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.DoubleFunction
import scala.Tuple2


data class PurchaseRecord(val user: String, val product: String, val price: String)

fun doubleFunction(block: (PurchaseRecord) -> Double) =
        object : DoubleFunction<PurchaseRecord> {
            override fun call(input: PurchaseRecord): Double = block(input)
        }

fun main(args: Array<String>) {
    val parallelism = 4
    val sc = JavaSparkContext("local[$parallelism]", "Simple Spark App")
    val data = sc.textFile("data/user_purchase_history.csv", parallelism)
            .map { it.split(",") }
            .map { PurchaseRecord(it[0], it[1], it[2]) }

    // 购买总数
    val numPurchases = data.count()

    // 客户总个数
    val uniqueUsers = data.map { it.user }.distinct().count()

    // 总收入
    val totalRevenue = data.mapToDouble<Double>(doubleFunction { it.price.toDouble() }).sum() // sum just on JavaDoubleRDD

    // 最畅销的产品
    val productByPopularity = data.mapToPair { Tuple2(it.product, 1) }
            .reduceByKey { a, b -> a + b }
            .collect()
            .sortedBy { -it._2 }
            .first()

    println("Total purchases: $numPurchases")
    println("Unique users: $uniqueUsers")
    println("Total revenue: $totalRevenue")
    println("Most popular product: ${productByPopularity._1} with ${productByPopularity._2} purchases")
}
