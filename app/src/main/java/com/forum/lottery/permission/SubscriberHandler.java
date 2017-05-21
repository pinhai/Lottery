package com.forum.lottery.permission;

import android.content.pm.PackageManager;

import java.util.LinkedList;
import java.util.List;

import rx.SingleSubscriber;

/**
 * Created by Administrator on 2016/10/1.
 */
public class SubscriberHandler {
    private SingleSubscriber<? super String[]> singleSubscriber;
    public SubscriberHandler(SingleSubscriber<? super String[]> singleSubscriber){
        this.singleSubscriber = singleSubscriber;
    }

    public void postResult(String[] permissions, int[] grantResults){
        List<String> deniedPermissions = new LinkedList<String>();
        for(int i = 0; i < grantResults.length; i++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                deniedPermissions.add(permissions[i]);
        }
        singleSubscriber.onSuccess(deniedPermissions.toArray(new String[deniedPermissions.size()]));
    }
}
