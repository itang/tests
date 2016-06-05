package demo

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.*
import javax.sql.DataSource

@SpringBootApplication
open class Application {
}


@Configuration
open class DataSourceConfig {

    @Bean
    open fun createDataSource(): DataSource = ComboPooledDataSource().apply {
        jdbcUrl = "jdbc:postgresql://172.17.0.2:5432/postgres?user=postgres&password=postgres"
        driverClass = "org.postgresql.Driver"
    }
}


@Entity
@Table(name = "employee", schema = "spring_data_jpa_example")
class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @Column(name = "firstname")
    var firstName: String? = null

    @Column(name = "lastname")
    var lastname: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "age")
    var age: Int? = null

    @Column(name = "salary")
    var salary: Int? = null
}

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
}

@RestController
class TestController {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @RequestMapping("/employee")
    fun getTest(): List<Employee> {
        return employeeRepository.findAll()
    }
}

fun main(args: Array<String>) {
    val ctx = SpringApplication.run(Application::class.java, *args)

    "http://localhost:8080/employee".httpGet().responseString { request, response, result ->
        //do something with response
        when (result) {
            is Result.Failure -> {
                val error: Exception? = result.getAs()
                println("exception: $error")
            }
            is Result.Success -> {
                val data: String? = result.getAs()
                if (data != null) {
                    println("data: $data")
                }
            }
        }
    }
}

