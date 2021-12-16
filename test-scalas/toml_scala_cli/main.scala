// using lib com.moandjiezana.toml:toml4j:0.7.2

import com.moandjiezana.toml.Toml

import java.io.File
import java.net.URI
import java.awt.Desktop

import scala.jdk.CollectionConverters._
import scala.util.chaining._

object Main {
  private def browserURL(url: String): Unit =
    if (
      Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(
        Desktop.Action.BROWSE
      )
    )
      Desktop.getDesktop.browse(URI(url))

  def main(args: Array[String]): Unit =
    Toml()
      .read(File("D:\\ProgramData\\bin\\jiayou.toml"))
      .getList("urls")
      .tap(println)
      .pipe(_.asInstanceOf[java.util.List[String]].asScala.foreach(browserURL))
}
