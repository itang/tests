import java.util.Date
import scala.collection.JavaConverters._
import util._

import scalaz.zio.{App, IO}
import scalaz.zio.console._

import java.io.IOException

object Main extends App {
  def run(argas: List[String]): IO[Nothing, ExitStatus] =
  myAppLogic.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

  def myAppLogic : IO[IOException, Unit] = 
  for {
    _ <- putStrLn("Hello! What is your name?")
    n <- getStrLn
    _ <- putStrLn(s"hello, ${n}, good to meet you!")
  } yield ()
}


