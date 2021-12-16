import java.nio.file.Files
import java.nio.file.Paths

import scala.jdk.CollectionConverters.*
import scala.sys.process.*
import scala.util.chaining.*

@main
def main_version(): Unit =
  val path = Paths.get(".scalafmt.conf")
  val VersionR = """.+"(.+)".*""".r
  val version =
    if path.toFile.isFile then
      Files
        .readAllLines(path).asScala
        .find(_.trim.startsWith("version"))
        .map { case VersionR(v) => v }
        .tap(it => println(s"version from file: $it"))
    else
      println("no .scalafmt.conf")
      None

  /*
  val cmd = version match
    case Some(v) => s"scala-cli fmt --scalafmt-tag v$v ."
    case None    => s"scala-cli fmt ."
   */
  val cmd =
    version.fold(s"scala-cli fmt .")(v => s"scala-cli fmt --scalafmt-tag v$v .")

  cmd.tap(println).!
