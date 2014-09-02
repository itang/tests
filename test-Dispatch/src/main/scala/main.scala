/**
 * main.scala
 */

import org.springframework.web.client.RestTemplate
import org.springframework.http.converter.StringHttpMessageConverter
import collection.JavaConversions._
import scatang._
import dispatch._
import Defaults._
import org.springframework.web.client.RestTemplate
import org.springframework.http.converter.StringHttpMessageConverter
import javax.net.ssl.SSLSession
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.X509TrustManager
import javax.net.ssl.TrustManager
import javax.net.ssl.SSLContext
import java.security.cert.X509Certificate

class NullX509TrustManager extends X509TrustManager {
  def checkClientTrusted(chain: Array[X509Certificate], authType: String): Unit = {
    System.out.println()
  }

  def checkServerTrusted(chain: Array[X509Certificate], authType: String): Unit = {
    System.out.println()
  }

  def getAcceptedIssuers(): Array[X509Certificate] = {
    Array[X509Certificate]();
  }
}

object Main {

  def main(args: Array[String]): Unit = {

    var sslc: SSLContext = SSLContext.getInstance("TLS")

    val trustManagerArray = Array[TrustManager](new NullX509TrustManager())
    sslc.init(null, trustManagerArray, null);

    HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());

    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
      def verify(hostname: String, session: SSLSession): Boolean = {
        return true
      }
    })

    val restTemplate = new RestTemplate(List(new StringHttpMessageConverter()))

    println(restTemplate.getRequestFactory().getClass())

    println(restTemplate.getForObject("https://localhost:8443/api/v1/server/info.json", classOf[String]))

    //    val svc = url("https://localhost:8080/api/v1/server/info.json").as_!("admin", "12345678").secure
    //    val ret = Http(svc OK as.String)
    //    for (s <- ret) {
    //      println(s)
    //    }
  }
}


