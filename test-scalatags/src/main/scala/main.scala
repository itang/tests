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
  }

  private def pretty(s: String) {
    val prettier = new scala.xml.PrettyPrinter(80, 2)
    println(prettier.format(scala.xml.XML.loadString(s.toString)))
  }
}
