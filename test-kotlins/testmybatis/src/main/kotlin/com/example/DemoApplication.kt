package com.example

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


//data class City(
//        val id: Int,                 //FIXME: 通过带参数构造函数初始化数据集, BitInt -> Long, 提示找不到对应的构造函数, 类型不匹配: No constructor found in com.example.City matching [java.lang.Long, java.lang.String, java.lang.String, java.lang.String]
//        val name: String,
//        val state: String,
//        val country: String
//)

class City {
    var id: Int? = null            // 通过默认构造函数 + setter时, BitInt 类型能够赋值给Int类型, MyBatis 做了隐式转换
    var name: String? = null
    var state: String? = null
    var country: String? = null

    override fun toString(): String {
        return "City(id=$id, name=$name, state=$state, country=$country)"
    }
}

@Mapper
interface CityMapper {

    @Select("SELECT id, name, state, country FROM city WHERE state = #{state}")
    fun findByState(state: String): List<City>
}


@SpringBootApplication
open class DemoApplication

@Service
@Suppress("unused")
open class Loader @Autowired constructor(private val cityMapper: CityMapper) {

    @PostConstruct
    fun sampleCommandLineRunner() {
        println("init...")
        cityMapper.findByState("CA").forEach(::println)
    }
}


fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}
