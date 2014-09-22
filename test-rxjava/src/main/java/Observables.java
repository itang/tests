import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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

		Observable.interval(1, TimeUnit.SECONDS).take(5).toBlocking()
				.forEach(new Action1<Long>() {
					@Override
					public void call(Long counter) {
						System.out.println("Got: " + counter);
					}
				});
	}
}
