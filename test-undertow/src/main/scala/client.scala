package test_undertow

import language.postfixOps

import org.springframework.web.client.RestTemplate
import org.springframework.http.converter.StringHttpMessageConverter
import collection.JavaConversions._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

import test_undertow.Rests._

object Client extends App {

  val restTemplate = new RestTemplate(List(new StringHttpMessageConverter()))
  val ret = restTemplate.get[String]("http://localhost:8080")
  assert(ret.isInstanceOf[String])
  println(ret)
  //  println(restTemplate.getForObject("http://localhost:8080", classOf[String]))

  val f1 = Future { restTemplate.get[String]("http://www.baidu.com") }
  val f2 = Future { restTemplate.get[String]("http://wwww.sogou.com") }

  val result = for { r1 ← f1; r2 ← f2 } yield (r1 + r2)

  result onSuccess {
    case r ⇒ printf("result: %s\n", r)
  }

  result onFailure {
    case e: Exception ⇒ e.printStackTrace()
  }

  Await.result(result, 1000 millis)
}
