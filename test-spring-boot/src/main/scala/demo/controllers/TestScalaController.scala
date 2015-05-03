package demo.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ ResponseBody, RequestMapping }

@Controller
@RequestMapping(Array("/scala"))
class TestScalaController extends BaseController {

  @RequestMapping(Array(""))
  @ResponseBody
  def index(): String = {
    s"V21: Hello, from scala (at ${new java.util.Date()})"
  }
}
