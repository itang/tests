package demo

import mu.KLogging
import org.apache.ibatis.annotations.*
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal


// domain
//  public demo.Goods(java.lang.String, java.lang.String, java.math.BigDecimal, java.lang.Integer, int, java.util.Date, java.util.Date);
// @Immutable
data class Goods(
        val id: String,
        val name: String,
        val price: java.math.BigDecimal?, // DECIMAL	<->	java.math.BigDecimal
        val amount: Int?,
        val lockAmount: Int,
        val createdAt: Date,
        val updateAt: Date?
)

// repository
@Mapper
interface GoodsRepository {

    @Select("select * from goods where id = #{id}")

    //java.lang.String, java.lang.String, java.math.BigDecimal, java.lang.Integer, java.lang.Integer, java.sql.Timestamp, java.sql.Timestamp
    @Results(id = "GoodsResult")
    @ConstructorArgs(
            Arg(column = "id", id = true, javaType = String::class),
            Arg(column = "name", javaType = String::class),
            Arg(column = "price", javaType = BigDecimal::class),
            Arg(column = "amount", javaType = Integer::class),
            Arg(column = "lock_amount", javaType = Int::class),
            Arg(column = "created_at", javaType = Date::class),
            Arg(column = "updated_at", javaType = Date::class)
    )
    fun getById(@Param("id") id: String): Goods?

    @Select("SELECT EXISTS(select id from goods where id = #{id})")
    fun existsById(@Param("id") id: String): Boolean

    @Update("update goods set amount = #{newAmount} where id = #{id} and lock_amount <= #{newAmount}")
    fun modifyAmount(@Param("id") id: String, @Param("newAmount") newAmount: Int): Int


    class AddLockAmountSqlBuilder { // just for demo
        fun sql(@Param("id") id: String, @Param("amount") amount: Int): String {
            //return "update goods set lock_amount = lock_amount + $amount where id = #{id} and lock_amount + $amount <= amount"
            return "update goods set lock_amount = lock_amount + $amount where id = #{id} and lock_amount + #{amount} <= amount"
        }
    }
    @UpdateProvider(type = AddLockAmountSqlBuilder::class, method = "sql")
    fun addLockAmount(@Param("id") id: String, @Param("amount") amount: Int): Int
}


// service
interface GoodsService {
    fun existsGoodsById(id: String): Boolean

    fun getGoodsById(id: String): Goods?

    fun modifyGoodsAmount(id: String, newAmount: Int)

    fun bookGoods(id: String, amount: Int)
}

@Service
@Transactional(readOnly = true)
@Suppress("unused")
//TODO: 定义业务异常
class GoodsServiceImpl @Autowired constructor(private val goodsRepository: GoodsRepository) : GoodsService {

    override fun existsGoodsById(id: String): Boolean {
        return goodsRepository.existsById(id)
    }

    override fun getGoodsById(id: String): Goods? {
        return goodsRepository.getById(id)
    }

    @Transactional
    override fun modifyGoodsAmount(id: String, newAmount: Int) {
        assert(newAmount >= 0)

        if (existsGoodsById(id)) {
            val updateRows = goodsRepository.modifyAmount(id, newAmount)
            if (updateRows == 0) {
                throw RuntimeException("调整的库存不够卖!")
            }
        } else {
            throw RuntimeException("商品不存在!")
        }
    }

    @Transactional
    override fun bookGoods(id: String, amount: Int) {
        assert(amount >= 0)

        if (existsGoodsById(id)) {
            val updateRows = goodsRepository.addLockAmount(id, amount)
            if (updateRows == 0) {
                throw RuntimeException("库存不足!")
            }
        } else {
            throw RuntimeException("商品不存在!")
        }
    }
}

// test
@Component
class Tester @Autowired constructor(private val goodsService: GoodsService) {
    companion object : KLogging()

    fun test() {
        logger.info {
            """existsGoodsById("1")： ${goodsService.existsGoodsById("1")}"""
        }

        logger.info {
            """existsGoodsById("2")： ${goodsService.existsGoodsById("2")}"""
        }
        val goods1 = goodsService.getGoodsById("1")
        logger.info { "getGoodsById(\"1\"): $goods1" }

        val goods2 = goodsService.getGoodsById("2")
        logger.info { "getGoodsById(\"2\"): $goods2" }
    }
}

//////////////////////////////////////////////////
@SpringBootApplication
@EnableTransactionManagement
open class Application


fun main(args: Array<String>) {
    val app = SpringApplication.run(Application::class.java, *args)
    val tester = app.getBean(Tester::class.java)
    tester.test()

    //app.beanDefinitionNames.forEach { println("$it: ${app.getBean(it)}") }
}

