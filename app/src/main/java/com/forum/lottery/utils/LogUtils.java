package com.forum.lottery.utils;

import android.util.Log;

/**
 * Created by Su on 2015/8/13.
 */
public class LogUtils {
    private static boolean isDebug = true;
    private static final String TAG = "xwph";
    private LogUtils(){

    }

    public static void setDebug(boolean isDebug){
        LogUtils.isDebug = isDebug;
    }

    public static void i(String msg){
        if(isDebug){
            Log.i(TAG, msg);
        }
    }
}
