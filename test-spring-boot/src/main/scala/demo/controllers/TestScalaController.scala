package demo.controllers

import java.util.Date

import scala.collection.JavaConverters._

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ ResponseBody, RequestMapping }
import org.springframework.ui.ModelMap

import demo.ext.BaseController

@Controller
@RequestMapping(Array("/test/scala"))
class TestScalaController extends BaseController {

  @RequestMapping(Array(""))
  @ResponseBody
  def index(): String = {
    s"V21: Hello, from scala (at ${new Date()})"
  }

  @RequestMapping(Array("/handlebars"))
  def index(model: ModelMap): String = {
    model.put("now", new Date)
    model.put("list", List("a", "b", "c").asJava)

    "/test/scala/handlebars"
  }
}
