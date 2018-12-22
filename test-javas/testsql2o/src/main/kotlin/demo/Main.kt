package demo

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.sql2o.Sql2o
import java.util.*

fun main(args: Array<String>) {
    val sql2o = Sql2o("jdbc:mysql://localhost:3306/testsql2o", "root", "123456")

    sql2o.deleteAll("user").let { println(it) }

    val rows = listOf(
            mapOf("id" to UUID.randomUUID().toString(), "age" to 38, "created_at" to Date(), "description" to "your desc", "del_flag" to 0, "name" to "itang0"),
            mapOf("id" to UUID.randomUUID().toString(), "age" to 38, "created_at" to "2018-12-02 12:24:56", "description" to "your desc", "del_flag" to false, "name" to "itang1"),
            mapOf("id" to UUID.randomUUID().toString(), "age" to 38, "created_at" to "2018-12-02", "description" to "your desc", "del_flag" to true, "name" to "itang2"),
            mapOf("id" to UUID.randomUUID().toString(), "age" to 38, "created_at" to "0000-00-00 12:24:56", "description" to "your desc", "del_flag" to "1", "name" to "itang3"),
            mapOf("id" to UUID.randomUUID().toString(), "age" to 38, "created_at" to "0000-00-00 12:24:00", "description" to "your desc", "del_flag" to "1", "name" to "itang4")
    )
    for (row in rows) {
        sql2o.insertRow("user", row)
    }

    val users = sql2o.list("user")
    println()
    users.first()!!.forEach { (k, v) -> println("$k: ${v.javaClass}") }
    for (user in users) {
        println(user)
    }

    println()
    JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    JSON.toJSONString(users, SerializerFeature.WriteDateUseDateFormat).let { println(it) }

    println()

    val s = """{"child_table": [{"name": 1}, {"name": "2"}], "del_flag":0,"name":"itang0","created_at":"2018-12-22 11:48:27","description":"your desc","id":"e58b25bc-9198-41b2-b8cf-8b0e57be2c0f","age":38}"""

    val ret = JSON.parse(s)
    println(ret.javaClass) //class com.alibaba.fastjson.JSONObject

    println()
    val om = ObjectMapper()
    val ret2: Map<String, Any> = om.readValue(s, object : com.fasterxml.jackson.core.type.TypeReference<Map<String, Any>>() {})
    println(ret2)
    ret2.forEach { (k, v) -> println("$k: ${v.javaClass}") }
    println()
    (ret2["child_table"] as ArrayList<Any>).forEach { v -> println(" ${v.javaClass}") }
}


fun Sql2o.deleteAll(tableName: String): Int {
    return this.open().use { conn ->
        conn.createQuery("truncate $tableName").executeUpdate().result
    }
}

fun Sql2o.insertRow(tableName: String, row: Map<String, Any>): Int {
    val sql = insertRowSql(tableName, row)
    println("sql: $sql")
    return this.open().use { conn ->
        conn.createQuery(sql).apply {
            row.forEach { this.addParameter(it.key, it.value) }
        }.executeUpdate().result
    }
}

fun Sql2o.list(tableName: String): List<Map<String, Any>> {
    return this.open().use { conn -> conn.createQuery("Select * from $tableName order by name asc").executeAndFetchTable().asList() }
}

fun Sql2o.insertRowSql(tableName: String, row: Map<String, Any>): String {
    return """INSERT INTO $tableName (${row.keys.joinToString(", ")}) VALUES (${row.keys.joinToString(", ") { ":$it" }})"""
}