package demo.impl

import demo.GreetService

class GreetServiceImpl1 extends GreetService {
  override def hello(name: String): String = s"Hello, $name"
}

class GreetServiceImpl2 extends GreetService {
  override def hello(name: String): String = s"你好, $name"
}