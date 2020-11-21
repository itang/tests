package com.example.demo

import org.springframework.web.bind.annotation.{GetMapping, RestController}

import scala.beans.BeanProperty

@RestController
class ScalaController {

  @GetMapping(value = Array("/helloscala"))
  //def hello = new Resp("from scala", "Hello, Scala") // enable on maven
  def hello = "from scala"


  @GetMapping(value = Array("/helloscala/javabean"))
  def helloWithJava = new JResp("from java with Java bean", "hello scala")


  @GetMapping(value = Array("/helloscala/scalabean"))
  def helloWithScala = new SResp(msg = "from scala with Scala bean", "hello scala")

  case class SResp(@BeanProperty val msg: String,
                   @BeanProperty val data: Any)

}
