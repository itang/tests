package test.testavospush;

import test.testavospush.client.AVOSClient;
import test.testavospush.client.impl.AVOSClientImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AVOSClient.class).to(AVOSClientImpl.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("id")).toInstance(
                "gqd0m4ytyttvluk1tnn0unlvmdg8h4gxsa2ga159nwp85fks");
        bind(String.class).annotatedWith(Names.named("key")).toInstance(
                "7gd2zom3ht3vx6jkcmaamm1p2pkrn8hdye2pn4qjcwux1hl1");
    }

}
