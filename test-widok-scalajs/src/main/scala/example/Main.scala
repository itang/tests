package example

//import java.text.SimpleDateFormat

import java.util.Date

import org.scalajs.dom
import scala.scalajs.js

import org.widok._
import org.widok.bindings.HTML

import js.Dynamic.{global => g}

/*
object Main extends PageApplication {
  /*
  implicit class WDate(d: Date) {
    def format(pattern:String ="yyyy-MM-dd HH:mm:ss"):String =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d)
  }*/

  def view() = Inline(
    HTML.Heading.Level1(s"Welcome to Widok1 ${new Date()}" )
    , HTML.Paragraph("This is your first application.")
  )

  def ready() {
    log("Page loaded.")
    js.Dynamic.global.alert("ss")
  }
}*/

//step2
object Routes {
  val main = Route("/", pages.Main)
  val test = Route("/test/:param", pages.Test)
  val notFound = Route("/404", pages.NotFound)
  val mulparams = Route("/mulparams/:name/:age", pages.Mulparams)
  val routes = Set(main, test, mulparams, notFound)
}

object Main extends RoutingApplication(
  Routes.routes
  , Routes.notFound
)

object pages {

  case class Main() extends Page {
    def view() = Inline(
      HTML.Paragraph(
        HTML.Raw("<h1>Test Widok</h1>")
        , HTML.Anchor("Link to second page").url(Routes.test("param", "first page")))
        , HTML.Anchor("Link to multiple params page").url(Routes.mulparams(Map("name" -> "itang", "age" -> "20")))
    )

    def ready(route: InstantiatedRoute): Unit = {
      log(s"Page 'main' loaded with route '$route'")
    }

    override def destroy(): Unit = {
      log("Page 'main' left")
    }
  }

  case class Mulparams() extends Page {
    val name = Channel[String]()
    val age = Channel[String]()

    def view() = Inline("Received parameter: ", HTML.Paragraph(name), age)

    def ready(route: InstantiatedRoute): Unit = {
      name := route.args("name")
      age := route.args("age")
    }
  }

  case class Test() extends Page {
    val query = Channel[String]()

    def view() = Inline("Received parameter: ", query)

    def ready(route: InstantiatedRoute) {
      query := route.args("param")
      g.alert(query.toString())
    }
  }

  case class NotFound() extends Page {
    def view() = HTML.Heading.Level1("Page not found")

    def ready(route: InstantiatedRoute) {
      dom.setTimeout(() => Routes.main().go(), 2000)
    }
  }

}