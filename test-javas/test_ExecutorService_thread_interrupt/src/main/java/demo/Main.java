package demo;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().isInterrupted());
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("hello");
                    System.out.println(Thread.currentThread().isInterrupted());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { //NOTICE: 调用处抛出InterruptedException异常，并且在抛出异常后立即将线程的中断标示位清除，即重新设置为false
                        System.out.println("ex: " + Thread.currentThread().isInterrupted());
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        System.out.println("ex: " + Thread.currentThread().isInterrupted());
                        //throw new RuntimeException(e);
                    }
                }
            }
        };
        t.start();
        Thread.sleep(500);
        t.interrupt();

        Thread.sleep(10000);
    }

    void test() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            //NOTICE: block on running task >= max thread number
            es.submit(() -> {
                try {
                    Thread.sleep(10000);
                    System.out.println(new Date() + " " + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            });
        }

        System.out.println("here1");

        Thread.sleep(5000);
        es.shutdown();
    }
}
