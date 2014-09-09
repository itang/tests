import java.io.File
import org.apache.commons.io.FileUtils

object Files {
  implicit class FileWrapper(file: File) {
    def bytes(): Array[Byte] = FileUtils.readFileToByteArray(file)
  }
}
