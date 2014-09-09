import org.apache.commons.io.FileUtils
import java.io.File

object Hex {
  val hexChars = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

  implicit class FileWrapper(file: File) {
    def bytes(): Array[Byte] = FileUtils.readFileToByteArray(file)

    def bytesAsHex(): String = {
      val chs = bytes() map { b ⇒
        val d = b & 0xff

        // "%02X" format d
        {
          val h = d >> 4
          val l = d & 0X0F
          hexChars(h) + "" + hexChars(l)
        }
      }

      val ss = (0 until chs.length) map { i ⇒ if (i % 2 == 1) " " else "" }
      val ret = (chs zip ss) map { case (v1, v2) ⇒ v1 + v2 }

      val sss = (1 to ret.length) map { i ⇒ if (i % 24 == 0) "\n" else "" }
      val rett = (ret zip sss) map { case (v1, v2) ⇒ v1 + v2 }

      rett mkString ("")
    }
  }
}
