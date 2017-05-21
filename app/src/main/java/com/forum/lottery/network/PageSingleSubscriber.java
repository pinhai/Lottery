package com.forum.lottery.network;

import android.content.Context;

import rx.SingleSubscriber;

/**
 * Created by Administrator on 2017/1/18.
 */

public class PageSingleSubscriber<T> extends SimpleSingleSubscriber<T> {
    private SingleSubscriber<T> subscriber;
    public PageSingleSubscriber(Context context, SingleSubscriber<T> subscriber) {
        super(context);
        this.subscriber = subscriber;
    }

    @Override
    public void onError(Throwable error) {
        super.onError(error);
        subscriber.onError(error);
    }

    @Override
    public void onSuccess(T value) {
        super.onSuccess(value);
        subscriber.onSuccess(value);
    }

    @Override
    public void onResult(T value) {

    }
}
