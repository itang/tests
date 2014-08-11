/**
 * Created by itang on 14-7-25.
 */
import groovy.sql.Sql
import java.sql.ResultSet

//@Grapes(
//        @Grab(group = 'com.h2database', module = 'h2', version = '1.4.180')
//)
class TestSql {
    static void main(String[] args) {
        def sql = Sql.newInstance("jdbc:h2:./db-test", "sa", "", "org.h2.Driver")
        sql.execute("create table if not exists user (id int, name varchar(100))")
        sql.executeInsert("insert into user values(1, 'itang')")
        sql.execute "insert into user values(0, 'user 0')"
        for (i in 0..100) {
            String s = "insert into user values($i, 'user ${i}')"
            println s
            sql.execute(s)
        }
        sql.eachRow("select * from user") { row ->
            println(row.id + ":" + row.name)
        }

        def countSQL = "select count(*) as count from user"

        sql.query(countSQL) { ResultSet rs ->
            while(rs.next()){
               // println(rs.getInt("count"))
                println("count: ${rs.toRowResult()['COUNT']}")
            }
        }

        println sql.firstRow(countSQL)["COUNT"]
        println sql.firstRow(countSQL).get("COUNT")
        println sql.firstRow(countSQL).get("count")
    }
}
