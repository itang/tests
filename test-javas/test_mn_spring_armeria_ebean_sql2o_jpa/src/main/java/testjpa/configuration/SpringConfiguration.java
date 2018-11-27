package testjpa.configuration;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

@Factory
//@EnableTransactionManagement
public class SpringConfiguration {

    /*
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testDB");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }*/

    //https://docs.micronaut.io/latest/guide/index.html#sqlSupport
    // @Bean
    // public PlatformTransactionManager annotationDrivenTransactionManager() {
    //      return new DataSourceTransactionManager(dataSource());
    // }

    // @Bean
    //  DataSourceTransactionManager txManager(DataSource dataSource) {
    //       return new DataSourceTransactionManager(dataSource);
    // }

    // current status: can't save data
    @Bean
    public Sql2o sql2o(DataSource dataSource) {
        System.out.println(dataSource.getClass());
        return new Sql2o(dataSource);
    }
}
