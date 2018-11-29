package demo;

import io.reactivex.Flowable;

public class Main {
    public static void main(String []args) {
       System.out.println("Hello, java");
       Flowable.just(1, 2).repeat().take(10).blockingForEach(System.out::println);
    }
}

