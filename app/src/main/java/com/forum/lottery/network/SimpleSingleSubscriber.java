package com.forum.lottery.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.forum.lottery.MainActivity;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.ui.BaseActivity;

import rx.SingleSubscriber;

/**
 * Created by Administrator on 2016/8/27.
 */
public abstract class SimpleSingleSubscriber<T> extends SingleSubscriber<T> {
    private BaseActivity activity;
    public SimpleSingleSubscriber(Context context){
        this.activity = (BaseActivity) context;
    }
    @Override
    public void onError(Throwable error) {
        if(error != null){
            error.printStackTrace();
        }
        Toast.makeText(MyApplication.getInstance(), "网络不给力，请重试", Toast.LENGTH_SHORT).show();
        finish();
    }

    protected void toastError(ResultData<T> result){
        if(result.getMsg() == null){
            Toast.makeText(MyApplication.getInstance(), "网络不给力，请重试", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MyApplication.getInstance(), result.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(T value) {
        if(value instanceof ResultData){
            ResultData<?> data = (ResultData<?>) value;
            if(data.getStatus() == 0){
                onResult(value);
            }else{
                if(data.getStatus() == -10001 && activity != null){
                    showLoginDialog(activity, data.getMsg());
                }else{
                    toastError((ResultData<T>) value);
                }
            }
        }else{
            onResult(value);
        }
        finish();
    }

    private void showLoginDialog(final Activity activity, String msg){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("state", -1);
        intent.putExtra("msg", msg);
        activity.startActivity(intent);
    }

    public abstract void onResult(T value);

    public void finish(){

    }
}
