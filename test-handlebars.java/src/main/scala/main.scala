/**
 * main.scala
 */

import collection.JavaConverters._
import java.util.{ ArrayList => JList }
import scatang._
import java.lang.management.ManagementFactory
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Template
import com.github.jknack.handlebars.Helper
import com.github.jknack.handlebars.Options
import scala.beans.BeanProperty

object Main {

  def main(args: Array[String]): Unit = {
    testHandlebars()
  }

  def testHandlebars() {
    implicit val handlebars = new Handlebars()

    val result = render("hello, {{this}}", "Handlebars.java")
    println(result)

    type JListString = JList[String]
    val users = new JListString
    users.add("1"); users.add("2"); users.add("3")

    val c1 = Map("title" -> "hello", "story" -> Map("name" -> "Handlebars.java rocks!",
      "users" -> users,
      "numbers" -> List(100, 200, 300).asJava).asJava,
      "map" -> Map("name" -> "itang", "age" -> 18).asJava).asJava
    println(render(s"""<h1>{{title}}</h1>
{{#story}}
<span>{{name}}</span>
{{/story}}
users: {{#each story.users}}  {{@index}}: {{this}}{{/each}}
numbers: {{# story.numbers}} {{@index}}: {{.}}, {{/story.numbers}}
map: {{#each map}}{{@key}} {{this}}, {{/each}}
""",
      c1))

    puts(render(s"""<h1>{{title}}</h1>
{{#with story}}
<span>{{name}}</span>
{{/with}}""",
      c1))

    puts(render(s"""{{#active}}hello active{{/active}}
                    |{{#if noempty}}no emtpy{{/if}}
                    |{{#if nil}}null{{/if}}
                    |{{#unless nil}}null{{/unless}}
                    |{{^nil}}null ^{{/nil}}""".stripMargin,
      Map("active" -> true, "noempty" -> List(), "nil" -> null).asJava))

    // {{name context? [argument]* [hash]*}}
    //{{#name context? [argument]* [hash]*}}
    //...
    //{{/name}}

    testHelper
  }

  case class Blog(@BeanProperty title: String, @BeanProperty content: String)

  def testHelper(implicit handlebars: Handlebars) {
    handlebars.registerHelper("blog", new Helper[Blog]() {
      override def apply(blog: Blog, options: Options): String = {
        val s: String = options.fn(blog)
        s"<b>${s}</b>"
      }
    })

    puts(render("{{#blog}}blog: {{title}}: {{content}}{{/blog}}", Blog("first blog", "test")))
  }

  private def puts(args: Any): Unit = println(args)

  private def render(tmp: String, context: AnyRef)(implicit handlebars: Handlebars): String = {
    val template: Template = handlebars.compileInline(tmp)
    template.apply(context)
  }
}
