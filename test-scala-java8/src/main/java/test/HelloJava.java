package test;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class HelloJava {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Hello, World!(From Java)");
        }).start();

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        list.stream().map(x -> x.toUpperCase()).collect(toList()).forEach(System.out::println);
    }
}
