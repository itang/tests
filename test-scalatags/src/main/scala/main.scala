/**
 * main.scala
 */

import scatang._

import scalatags.Text.all._

object Main {

  def main(args: Array[String]): Unit = {
    val fram =
      html(
        head(
          script("""alert("hello")""")
        ),
        body(
          h1("Hello"),
          button(onclick := """alert("here")""") {
            "点我"
          },
          raw("<script>alert('hello!')</script>")
        )
      )

    pretty(fram.toString())
    /*
<html>
  <head>
    <script>alert(&quot;hello&quot;)</script>
  </head>
  <body>
    <h1>Hello</h1>
    <button onclick="alert(&quot;here&quot;)">点我</button>
    <script>alert('hello!')</script>
  </body>
</html>
*/

  test()
}

private def test() {
  val s = """autoconfig

Displays an auto-configuration report showing all auto-configuration candidates and the reason why they “were” or “were not” applied.

true

beans

Displays a complete list of all the Spring Beans in your application.

true

configprops

Displays a collated list of all @ConfigurationProperties.

true

dump

Performs a thread dump.

true

env

Exposes properties from Spring’s ConfigurableEnvironment.

true

health

Shows application health information (defaulting to a simple “OK” message).

false

info

Displays arbitrary application info.

false

metrics

Shows “metrics” information for the current application.

true

mappings

Displays a collated list of all @RequestMapping paths.

true

shutdown

Allows the application to be gracefully shutdown (not enabled by default).

true

trace

Displays trace information (by default the last few HTTP requests).

true"""

    val lines = s.split("\n").filter(it => it.trim != "")
    val words = for (i <- (0 until lines.size) if i % 3 == 0) yield lines(i)

    val h = ul(
      for (it <- words) yield li(a(href := s"/manage/$it", target := "_blank")(it))
    )

    pretty(h.toString)
  }


  private def pretty(s: String) {
    val prettier = new scala.xml.PrettyPrinter(80, 2)
    println(prettier.format(scala.xml.XML.loadString(s.toString)))
  }
}
