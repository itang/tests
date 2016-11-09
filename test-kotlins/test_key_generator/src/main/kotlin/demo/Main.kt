package demo

import org.apache.commons.codec.binary.Hex
import java.security.SecureRandom
import java.util.*
import javax.crypto.KeyGenerator


fun main(args: Array<String>) {
    val kgs = listOf(KeyGenerator.getInstance("AES").apply { init(SecureRandom()) },
            KeyGenerator.getInstance("AES").apply { init(128) },
            KeyGenerator.getInstance("HmacSHA1").apply { init(128) })

    for (kg in kgs) {
        val key = kg.generateKey()
        val base64 = String(Base64.getUrlEncoder().encode(key.encoded))
        val abase64 = String(org.apache.commons.codec.binary.Base64.encodeBase64URLSafe(key.encoded))
        val hex = Hex.encodeHexString(key.encoded)

        println("hex: $hex(${hex.length})  base64: $base64(${base64.length}) abase64: $abase64(${abase64.length})")
    }

}

