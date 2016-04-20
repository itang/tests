package demo

import org.msgpack.MessagePack
import org.msgpack.template.Templates
import org.msgpack.type.Value
import org.msgpack.unpacker.Converter
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val src = arrayListOf("msgpack", "kumofs", "viver");
    val msgpack = MessagePack()

    val raw: ByteArray = msgpack.write(src)

    BufferedOutputStream(FileOutputStream("t.bat")).use {
        it.write(raw)
    }

    val content = ByteArray(raw.size)
    BufferedInputStream(FileInputStream("t.bat")).use {
        it.read(content)
    }

    val dst1: List<String> = msgpack.read(content, Templates.tList(Templates.TString))

    for (e in dst1) {
        println(e)
    }

    println("=".repeat(10))

    val dynamic: Value = msgpack.read(raw)
    val dst2: List<String> = Converter(dynamic).read(Templates.tList(Templates.TString))

    dst2.forEach { println(it) }
}

