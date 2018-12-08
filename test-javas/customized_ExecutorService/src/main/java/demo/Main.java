package demo;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = new ThreadPoolExecutor(5,
                10, // min thread keep in pool
                10 * 1000,
                TimeUnit.MILLISECONDS, //空闲线程(maxpoolsize -corepoolsize) 失效时间
                new LinkedBlockingQueue(2),
                new ThreadFactory() { // 线程创建工厂

                    private final AtomicInteger mThreadNum = new AtomicInteger(0);
                    private static final String PREFIX_NAME = "command-bus-worker";

                    @Override
                    public Thread newThread(Runnable r) {
                        String name = PREFIX_NAME + "-thread-" + mThreadNum.incrementAndGet();
                        Thread t = new Thread(r, name);
                        System.out.println("thread: " + name + " has been created.");
                        return t;
                    }
                }, new MyAbortPolicy()/*任务过载拒绝策略*/
        );
        for (int i = 0; i < 100; i++) {
            try {
                // https://www.jianshu.com/p/f030aa5d7a28
                es.execute(new MyTask("MyTask_" + i));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        Thread.sleep(10000);
        System.out.println("end");

        es.shutdown();
    }

    public static class MyTask implements Runnable {

        private final String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + Thread.currentThread());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static class MyAbortPolicy extends ThreadPoolExecutor.AbortPolicy {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.out.println(r.getClass());
            System.err.println(r + " rejected");
            super.rejectedExecution(r, e);
        }
    }
}

