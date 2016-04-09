package demo

import java.io.FileOutputStream
import java.io.File
import java.io.FileInputStream

fun main(args: Array<String>) {
    val i = 254
    val bytes = ByteArray(1)
    bytes[0] = i.toByte()

    val file = "../data.bat"
    FileOutputStream(File(file)).use {
        it.write(bytes)
    }

    FileInputStream(File(file)).use {
        val bytes = ByteArray(1)
        it.read(bytes)
        println(bytes[0])  // -2
    }
}
