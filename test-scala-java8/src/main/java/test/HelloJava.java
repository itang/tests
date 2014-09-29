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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloJava {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        go(() -> {
            sleep(200, TimeUnit.MILLISECONDS);
            println("Hello, World!(From Java)");
        });

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");

        Future<List<String>> ret = go(() -> {
            List<String> it = list.stream().map(x -> x.toUpperCase()).collect(toList());
            it.forEach(HelloJava::println);
            return it;
        });
        ret.get(1, TimeUnit.SECONDS);

        closeSystem(2, TimeUnit.SECONDS);
    }

    private static void go(Runnable runnable) {
        pool.execute(runnable);
    }

    private static <T> Future<T> go(Callable<T> callable) {
        return pool.submit(callable);
    }

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private static void closeSystem(int v, TimeUnit tu) {
        pool.shutdown();
        try {
            pool.awaitTermination(v, tu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int v, TimeUnit tu) {
        try {
            Thread.sleep(tu.toMillis(v));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void println(String msg) {
        out.println(Thread.currentThread() + ": " + msg);
    }
}
