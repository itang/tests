package test_undertow

import org.springframework.web.client.RestTemplate
import org.springframework.http.converter.StringHttpMessageConverter

import collection.JavaConversions._

object Client extends App {
  val restTemplate = new RestTemplate(List(new StringHttpMessageConverter()))

  println(restTemplate.getForObject("http://localhost:8080", classOf[String]))
}
