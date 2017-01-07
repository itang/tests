package demo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals
import kotlin.test.fail

@RunWith(SpringRunner::class)
@SpringBootTest()
@Transactional
open class TestDemo {

    @Autowired
    private lateinit var goodsService: GoodsService

    val MAX_AMOUNT = 25

    @Test
    @Rollback
    fun testModifyGoodsAmount() {
        goodsService.modifyGoodsAmount("1", 20)
        assertEquals(20, goodsService.getGoodsById("1")?.amount)
    }


    @Test
    @Rollback
    fun testBookGoods() {
        goodsService.bookGoods("1", 5)
        assertEquals(5, goodsService.getGoodsById("1")?.lockAmount)

        goodsService.bookGoods("1", 1)
        assertEquals(6, goodsService.getGoodsById("1")?.lockAmount)

        try {
            goodsService.bookGoods("1", MAX_AMOUNT)
            fail()
        } catch (e: Exception) {

        }
    }

    @Test
    @Rollback
    fun testBookGoodsMultiThreads() {
        val threads = 100
        //val cdl = java.util.concurrent.CountDownLatch(threads)
        val list = (1..threads).map {
            Thread({
                try {
                    goodsService.bookGoods("1", 1)
                } finally {
                    //cdl.countDown()
                }
            }).apply {
                start()
            }
        }
        //cdl.await()
        list.forEach(Thread::join)

        assertEquals(MAX_AMOUNT, goodsService.getGoodsById("1")?.lockAmount)
    }
}

