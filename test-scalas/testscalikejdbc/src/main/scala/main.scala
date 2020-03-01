//import scala.collection.JavaConverters._
import scalikejdbc._
import util.time

object Main {
  def main(args: Array[String]): Unit = time {
    val pool = ConnectionPool.singleton(
      "jdbc:mysql://localhost:3306/testdb?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai",
      "root",
      "123456"
    )
    using(ConnectionPool.borrow()) { db =>
    }

    DB.readOnly { implicit session =>
      val rows = sql"""select * from user"""
        .map(it => it.any("name"))
        .list()
        .apply()
      println(rows)
    }
  }
}
