package com.forum.lottery.api;

import com.forum.lottery.entity.RegisterResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * Created by Administrator on 2017/5/17.
 */

public interface UserService {
    /**
     * 注册
     * @return
     */
    @POST("nonAuthority/register/regist")
    @FormUrlEncoded
    Single<RegisterResult> register(@Field("username") String userName, @Field("password") String password, @Field("authnum") String code);

    /**
     * 登录
     * @return
     */
    @POST("nonAuthority/userLogin?isapp=1")
    @FormUrlEncoded
    Single<RegisterResult> login(@Field("username") String userName, @Field("password") String password);
}
