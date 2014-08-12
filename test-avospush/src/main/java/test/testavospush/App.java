package test.testavospush;

import test.testavospush.client.AVOSClient;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Hello world!
 * 
 */
public class App {
    private static final String TEST_IID = "ec59d6cf-d013-47eb-9b67-9eef91147218";

    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new AppModule());
        final AVOSClient client = injector.getInstance(AVOSClient.class);

        String ret = client.pushToInstallationId(TEST_IID, "hello world");
        System.out.println("返回结果:" + ret);
    }
}
