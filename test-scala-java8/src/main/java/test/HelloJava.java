package test;

import static java.util.stream.Collectors.toList;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloJava {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        go(() -> {
            System.out.println("Hello, World!(From Java)");
        });

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        Future<List<String>> ret = go(() -> list.stream().map(x -> x.toUpperCase()).collect(toList()));
        ret.get().forEach(out::println);
    }

    private static void go(Runnable runnable) {
        pool.execute(runnable);
    }

    private static <T> Future<T> go(Callable<T> callable) {
        return pool.submit(callable);
    }

    private static ExecutorService pool = Executors.newCachedThreadPool();
}
