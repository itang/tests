package demo;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread());

        CompletableFuture<Void> f = CompletableFuture.completedFuture("hello").thenAccept(s -> {
            System.out.println(Thread.currentThread());
        });
        Thread.sleep(1000);
        System.out.println();

        //thenAccept execution on main thread(the call thread)
        CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread())).thenAccept(it -> System.out.println(Thread.currentThread()));

        Thread.sleep(1000);
        System.out.println();

        //thenAcceptAsync execute on fork-join pool
        CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread())).thenAcceptAsync(it -> System.out.println(Thread.currentThread()));
    }
}
