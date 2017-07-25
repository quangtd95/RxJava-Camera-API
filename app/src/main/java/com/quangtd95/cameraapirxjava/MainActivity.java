package com.quangtd95.cameraapirxjava;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        demo1();
//        demo2();
//        demo3();
//        demo4();
        demo5();
    }

    /**
     * flatMap
     * filter
     * take
     * doOnNext
     */
    private void demo5() {
        Api api = text -> {
            List<String> strings = new ArrayList<>();
            String[] ss = text.split(" ");
            Collections.addAll(strings, ss);
            return Observable.just(strings);
        };
        Subscription subscription = api.query("Hello world from Viet Nam")
                .flatMap(Observable::from)
                .filter(s -> (!s.equals("Hello")))
                .take(3)
                .doOnNext(s -> Log.e("TAGG", "do on next +" + s))
                .subscribe(s -> Log.e("TAGG", s));
//        subscription.unsubscribe();
    }

    private interface Api {
        Observable<List<String>> query(String text);
    }

    /**
     * map 2
     */
    private void demo4() {
        Observable.just("Hello world")
                .map(s -> s + " -Quang")
                .map(String::hashCode)
                .map(integer -> Integer.toString(integer))
                .subscribe(s -> Log.e("TAGG", s));
    }

    /**
     * map
     */
    private void demo3() {
        Observable.just("Hello world")
                .map(s -> s + " -quang")
                .subscribe(s -> Log.e("TAGG", s));

    }

    /**
     * rút gọn code
     */
    private void demo2() {
        Observable<String> myObservable = Observable.just("Hello world");
        myObservable.subscribe(s -> Log.e("TAGG", s));
    }

    /**
     * tạo 1 observable
     */
    private void demo1() {
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello world");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(String s) {
                Log.e("TAGG", s);
            }
        };
        myObservable.subscribe(mySubscriber);
    }
}
