package demo.refreshable;

import io.micronaut.runtime.context.scope.Refreshable;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Refreshable
public class WeatherService {
    private String forecast;

    public WeatherService() {
        System.out.println("construct..."+this);
    }

    @PostConstruct
    public void init() {
        System.out.println("init...");
        String now = new SimpleDateFormat("dd/MMM/yy HH:ss.SSS").format(new Date());
        this.forecast = "Scattered Clouds " + now;
    }

    public String latestForecast() {
        return forecast;
    }
}
