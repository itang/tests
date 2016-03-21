/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package sample.helloworld.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

/**
 * The hello service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the HelloService.
 */
public interface HelloService extends Service {

  /**
   * Example: curl http://localhost:9000/api/hello/Alice
   */
  ServiceCall<String, NotUsed, String> hello();

  /**
   * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
   * "Hi"}' http://localhost:9000/api/hello/Alice
   */
  ServiceCall<String, GreetingMessage, Done> useGreeting();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("helloservice").with(
        restCall(Method.GET,  "/api/hello/:id",       hello()),
        restCall(Method.POST, "/api/hello/:id",       useGreeting())
      ).withAutoAcl(true);
    // @formatter:on
  }
}
