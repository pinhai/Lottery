package com.forum.lottery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;


public class ShowTiemTextView extends android.support.v7.widget.AppCompatTextView implements Runnable{

    private boolean run = false; //觉得是否执行run方法
    private int time;         
    public ShowTiemTextView(Context context) {
        super(context);
    }

    public ShowTiemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setTime(int time){  //设定初始值
        this.time = time;  
    }
    public boolean isRun(){
        return run;
    }
    public void beginRun(){
        this.run = true;
        run();
    }
    public void stopRun(){
        this.run = false;
    }
    @Override
    public void run() {
        if (run){
            ComputeTime();
//            String showTime = "";
//            if(time < 60){
//                showTime = "00:00:" +  time + "";
//            }else if(time < 60*60){
//                showTime = "00:" + time/60 + ":" + time%60;
//            }else {
//                showTime = time
//            }
            this.setText(secToTime(time));
            postDelayed(this, 1000);
//            try{
//                Thread.sleep(1000);
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
        }else{
            removeCallbacks(this);
        }
    }

    private void ComputeTime(){
        time--;
        if (time==0)
            stopRun();
    }

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}