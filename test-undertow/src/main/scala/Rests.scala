package test_undertow

import org.springframework.web.client.RestTemplate

object Rests {
  implicit class RestTemplateWrapper(restTemplate: RestTemplate) {
    def get[T](url: String)(implicit m: Manifest[T]): T =
      restTemplate.getForObject(url, m.runtimeClass).asInstanceOf[T]
  }
}