package foo

import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Example extends App {
  val keySpec = new SecretKeySpec("qnscAdgRlkIhAUPY44oiexBKtQbGY0orf7OV1I50".getBytes, "HmacSHA1")

  val mac = Mac.getInstance("HmacSHA1")
  mac.init(keySpec)
  val result = mac.doFinal("foo".getBytes)

  println(Base64.getEncoder.encodeToString(result))
}
