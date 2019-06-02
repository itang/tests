import java.util.Date
import scala.collection.JavaConverters._
import util._
import org.sql2o._

object Main {

  def main(args: Array[String]): Unit = time {
    val sql2o = new Sql2o(
      "jdbc:mysql://localhost:3306/test_bin",
      "root",
      "123456"
    ).$$(_.setDefaultCaseSensitive(true))

    val dao = sql2o.open()

    /*
    val md = dao
      .getJdbcConnection()
      .getMetaData()
      .getTables(null, null, null, Array("d"))
     */
    val columns =
      dao.getJdbcConnection.getMetaData.getColumns(null, null, "d", null)
    new Iterator[String] {
      def hasNext: Boolean = columns.next()
      def next(): String = columns.getString("COLUMN_NAME")
    }.foreach(println)

    println("=" * 100)
    //while (columns.next()) {
    // println(columns.getString("COLUMN_NAME"))
    // }

    val s = dao.getJdbcConnection().createStatement()
    s.execute("select userName as UserName from d")
    val rs = s.getResultSet()
    val rsmd = rs.getMetaData()
    val cs = rsmd.getColumnCount()
    while (rs.next()) {
      rs.getString("USERNAME") |> (println)
      rs.getString("username") |> (println)
      (1 to cs).foreach(i => {
        val cname = rsmd.getColumnName(i)
        val cvalue = rs.getObject(cname)
        println(s"$cname: $cvalue")
        println(s"${rsmd.getColumnLabel(i)}: $cvalue")
      })
    }
    s.close()

    val m = dao.createQuery("select * from d").executeAndFetchTable().asList()
    println(m)
    println(m)

    dao.close()
  }
}
