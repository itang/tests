/**
 * main.scala
 */

import java.io.File

import org.apache.commons.io.FileUtils
import collection.JavaConversions._
//import collection.JavaConverters._

object Main {

  val hs = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

  def main(args: Array[String]): Unit = {
    println(test("target/scala-2.11/classes/Main.class"))
  }

  def test(classPath: String): String = {
    var bytes = FileUtils.readFileToByteArray(new File(classPath))
    val chs = bytes.map { b =>
      val d = b & 0xff
      val h = d >> 4
      val l = d & 0X0F
      hs(h) + "" + hs(l)
      //"%02X" format d
    }

    val ss = (0 until bytes.length).map(i => if (i % 2 == 1) " " else "")
    val ret = chs.zip(ss).map(it => it._1 + it._2)
    //    for(line <- ret){
    //      println(s"""'$line'""")
    //    }

    println("*" * 100)

    val sss = (1 to ret.length).map(i => if (i % 24 == 0) "\n" else "")
    val rett = (ret zip sss) map (it => it._1 + it._2)
    rett mkString ("")
  }
}
