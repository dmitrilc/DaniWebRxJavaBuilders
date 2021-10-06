package com.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Entry {
    public static void main(String[] args) {
        Observable<Long> obs = Observable.intervalRange(1L, 5L, 0L, 2L, TimeUnit.SECONDS); //1

        //subWithObserver(obs);
        subWithConsumers(obs);
    }

    private static <T> void subWithObserver(Observable<T> obs){ //1

        obs.blockingSubscribe(new Observer<>(){ //2
            private Disposable disposable; //3

            @Override
            public void onSubscribe(@NonNull Disposable d) { //4
                this.disposable = d; //5
            }

            @Override
            public void onNext(@NonNull T item) { //6
                System.out.println("Received in Observer: " + item); //7
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose(); //8
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable.dispose(); //9
                System.out.println("Complete"); //10
            }
        });
    }

    private static <T> void subWithConsumers(Observable<T> obs){
        obs.blockingSubscribe(
                item -> System.out.println("Received in Consumer: " + item),
                error -> error.printStackTrace(),
                () -> System.out.println("Complete")
        );
    }
}