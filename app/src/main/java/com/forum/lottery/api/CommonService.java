package com.forum.lottery.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface CommonService {
    @GET("validateCode?t=0.39114135348933443")
    Single<ResponseBody> loadCodeImage();
//    Single<ResponseBody> loadCodeImage(@Query("t") String t);
}
