package demo;

import demo.anno.Bar;
import demo.anno.Foo;
import demo.factories.Engineable;
import demo.named.MyVehicle;
import demo.primary.ColorPicker;
import demo.refreshable.WeatherService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;

import javax.inject.Singleton;


public class Main {
    public static void main(String[] args) {
        try (ApplicationContext context = ApplicationContext.run()) {
            MyBean myBean = context.getBean(MyBean.class);
            // do something with your bean
            System.out.println(myBean);

            Engine engine = context.getBean(Engine.class);
            System.out.println(engine.start());

            MyVehicle myVehicle = context.getBean(MyVehicle.class);
            myVehicle.start();

            ColorPicker picker = context.getBean(ColorPicker.class);
            System.out.println(picker.color());
            context.getBeansOfType(ColorPicker.class).forEach(System.out::println);

            WeatherService weatherService = context.getBean(WeatherService.class);
            System.out.println("1 " + weatherService.latestForecast());
            System.out.println("2 " + context.getBean(WeatherService.class).latestForecast());
            context.publishEvent(new RefreshEvent());
            System.out.println("3 " + weatherService.latestForecast());
            System.out.println("4 " + context.getBean(WeatherService.class).latestForecast());

            System.out.println(context.getBean(Foo.class));
            System.out.println(context.getBean(Foo.class));

            System.out.println(context.getBean(Bar.class));
            System.out.println(context.getBean(Bar.class));


            System.out.println(context.getBean(Engineable.class).start());
        }
    }

    @Singleton
    public static class MyBean {
        @Override
        public String toString() {
            return "MyBean()";
        }
    }

    interface Engine {
        String start();
    }

    @Singleton
    public static class V8Engine implements Engine {

        @Override
        public String start() {
            return "V8 Engine start...";
        }
    }
}
