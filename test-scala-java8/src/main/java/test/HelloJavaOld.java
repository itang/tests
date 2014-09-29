package test;

import java.util.ArrayList;
import java.util.List;

public class HelloJavaOld {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello, World!(From Java)");
            }
        }).start();

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        List<String> newList = new ArrayList<>();
        for (String x : newList) {
            newList.add(x.toUpperCase());
        }

        for (String x : newList) {
            System.out.println(x);
        }
    }
}
