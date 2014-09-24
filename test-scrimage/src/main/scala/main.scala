/**
 * main.scala
 */

import scatang._
import java.io.File

import com.sksamuel.scrimage._
//import com.sksamuel.scrimage.Format.PNG

object Main {

  def main(args: Array[String]): Unit = {
    val in = new File("logo.jpg")
    val out = new File("logo-output-80_80.png")
    Image(in).trim(0, 0, 272 - 72, 0).fit(80, 80).write(out)
    Image(out).fit(16, 16).write(new File("logo-output-16_16.png"))
  }
}


