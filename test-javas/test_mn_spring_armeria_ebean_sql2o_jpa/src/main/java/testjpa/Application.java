package testjpa;

import com.linecorp.armeria.server.Server;
import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    public static void main(String[] args) {
        /*//https://micronaut-projects.github.io/micronaut-spring/latest/guide/index.html#springParentContext
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        MicronautApplicationContext context = new MicronautApplicationContext();
        context.start();
        builder.parent(context);
        builder.sources(Application.class);
        builder.build().run();
*/
        Micronaut.run(Application.class).getBean(Server.class).start().join();

        // try (ApplicationContext ctx = ApplicationContext.run()) {
        //    ctx.getBean(Server.class).start().join();
        //   }
    }

    //@Singleton
    public static class Bootstrap {
        private Logger logger = LoggerFactory.getLogger(this.getClass());
        private final Server server;

        public Bootstrap(Server server) {
            System.out.println("init Bootstrap");
            this.server = server;
        }

        //@PostConstruct
        public void init() {
            logger.info("start server...");
            server.start();
        }
    }
}
