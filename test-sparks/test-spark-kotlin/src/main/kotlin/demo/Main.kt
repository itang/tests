package demo

import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD

fun main(args: Array<String>) {
    val conf = SparkConf().setAppName("Test Spark App").setMaster("local[4]")
    val sc = JavaSparkContext(conf)

    val buildGradleTextRdd: JavaRDD<String> = sc.textFile("build.gradle", 2)
    println("line count: ${buildGradleTextRdd.count()}")

    //map-reduce
    val ret: List<Pair<String, Int>> = buildGradleTextRdd.map { Pair(it, it.length) }.collect()
    ret.forEach { println("${it.second}:${it.first}") }
}
