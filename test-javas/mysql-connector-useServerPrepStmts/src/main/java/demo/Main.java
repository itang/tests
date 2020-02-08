package demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb?disableMariaDbDriver");
        //config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false");
        //config.setJdbcUrl("jdbc:mariadb://localhost:3306/testdb?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false");
        //config.setJdbcUrl("jdbc:mariadb://localhost:3306/testdb");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        HikariDataSource ds = new HikariDataSource(config);
        Connection conn = ds.getConnection();
        System.out.println(conn.getClientInfo());
        System.out.println(conn.getClass());
        System.out.println(conn.getMetaData());

        conn.createStatement().execute("drop table if exists mysql_bug_test");

        conn.createStatement().execute("CREATE TABLE mysql_bug_test (\n" +
                "\tid varchar(100) NULL,\n" +
                "\tname varchar(100) NULL\n" +
                ")");
        conn.createStatement().execute("INSERT INTO mysql_bug_test (id,name) VALUES \n" +
                "('1','test1')\n" +
                ",('2','test2')");
        PreparedStatement st2 = conn.prepareStatement("select * from mysql_bug_test where id=?");
        st2.setString(1, "1");
        ResultSet rs2 = st2.executeQuery();
        while (rs2.next()) {
            String name = rs2.getString("name");
            System.out.println(name);
        }
        System.out.println("=========");

        conn.createStatement().execute("ALTER TABLE mysql_bug_test ADD segment1 varchar(100) NULL");
        st2.setString(1, "1");
        ResultSet rs21 = st2.executeQuery();
        while (rs21.next()) {
            String name = rs21.getString("name");
            System.out.println(name);
        }
        PreparedStatement st3 = conn.prepareStatement("select * from mysql_bug_test where id=?");
        st3.setString(1, "1");
        ResultSet rs3 = st2.executeQuery();
        while (rs3.next()) {
            String name = rs3.getString("name");
            System.out.println(name);
        }
    }
}

