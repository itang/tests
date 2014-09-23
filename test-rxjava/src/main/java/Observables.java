import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class Observables {

	public static void main(String[] args) {
		Observable.just(1, 2, 3).doOnNext(new Action1<Integer>() {
			@Override
			public void call(Integer t1) {
				if (t1.equals(2)) {
					throw new RuntimeException("I don't like 2");
				}
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("Completed");

			}

			@Override
			public void onError(Throwable e) {
				System.out.println("Oh:" + e.getMessage());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println("Got " + t);
			}
		});

		Observable.interval(100, TimeUnit.MILLISECONDS).take(5).toBlocking()
				.forEach(new Action1<Long>() {
					@Override
					public void call(Long counter) {
						System.out.println("Got: " + counter);
					}
				});

		Observable.just("1", "2", "3")
				.flatMap(new Func1<String, Observable<Integer>>() {
					@Override
					public Observable<Integer> call(String id) {
						System.out.println(Thread.currentThread());
						return Observable.just(Integer.parseInt(id));
					}
				}).subscribe(new Action1<Integer>() {
					@Override
					public void call(Integer document) {
						System.out.println("Got: " + document);
					}
				});

		// Transforming Observables
		Observable.interval(10, TimeUnit.MILLISECONDS).take(20)
				.map(new Func1<Long, String>() {
					@Override
					public String call(Long input) {
						if (input % 3 == 0) {
							return "Fizz";
						} else if (input % 5 == 0) {
							return "Buzz";
						}
						return Long.toString(input);
					}
				}).toBlocking().forEach(new Action1<String>() {
					@Override
					public void call(String s) {
						System.out.println(s);
					}
				});
	}
}
