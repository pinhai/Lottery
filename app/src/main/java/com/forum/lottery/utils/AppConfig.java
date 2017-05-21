package com.forum.lottery.utils;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AppConfig {
    private static final AppConfig appConfig = new AppConfig();
    private AppConfig(){
    }

    public static final String FILE_NAME = "app_config";
    private static final long CACHE_TIME = 24 * 60 * 60 * 1000;
    public static AppConfig newInstance(){
        return appConfig;
    }
}
