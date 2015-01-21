package tryokhttp

import java.util.concurrent.TimeUnit

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.asScalaSet
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.squareup.okhttp.ResponseBody

object TryOkHttp {

  def echo(s: String) = s

  val url = "http://news.sina.com.cn"

  def main(args: Array[String]): Unit = {
    val client = new OkHttpClient()
    client.setConnectTimeout(5, TimeUnit.SECONDS)

    //client.setReadTimeout(100, TimeUnit.MILLISECONDS)
    client.setReadTimeout(5000, TimeUnit.MILLISECONDS)

    val response = client.get(url)
    val headers = response.headers()

    for ((name, value) <- (0 to headers.size()).map(i => (headers.name(i), headers.value(i)))) {
      println(s"$name=$value")
    }

    for {
      name <- response.headers().names();
      value <- response.headers().values(name)
    } {
      println(s"$name=$value")
    }

    println(client.getString(url))

    //test https
    println("*" * 100)
    println(client.getString("https://www.baidu.com/"))
  }

  implicit class StringWrapper(str: String) {
    def transcoding(from: String, to: String) = new String(str.getBytes(from), to)
  }

  implicit class ResponseWrapper(response: Response) {
    def hasContentTypeContainsCharset: Boolean = response.headers().get("Content-Type").contains("charset")
  }

  implicit class OkHttpClientWrapper(client: OkHttpClient) {
    def get(url: String): Response = {
      val request = new Request.Builder().url(url).get().build()
      client.newCall(request).execute()
    }

    def getString(url: String): String = {
      val response = get(url)

      if (response.hasContentTypeContainsCharset) {
        response.body.string()
      } else {
        val charset = response.body().guessCharset()
        //TODO 直接转码为什么不行
        //val content = response.body.string()
        //new String(content.getBytes("UTF-8"), charset.getOrElse("utf-8"))
        //content.transcoding("UTF-8", "ISO-8859-1").transcoding("ISO-8859-1", charset.getOrElse("utf-8"))

        new String(get(url).body().bytes(), charset.getOrElse("utf-8"))
      }
    }
  }

  object ResponseBodyWrapper {
    val CHARSET_PLACEHOLDER = "text/html; charset="
  }

  implicit class ResponseBodyWrapper(body: ResponseBody) {

    import ResponseBodyWrapper.CHARSET_PLACEHOLDER

    def guessCharset(): Option[String] = {
      val content = body.string()
      val pos = content.indexOf(CHARSET_PLACEHOLDER)

      if (pos == -1) {
        None
      } else {
        val start = pos + CHARSET_PLACEHOLDER.length
        val end = content.indexOf("\"", start)
        val charset = content.substring(start, end)
        Some(charset)
      }
    }
  }

}
