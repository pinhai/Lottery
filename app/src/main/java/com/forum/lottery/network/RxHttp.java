package com.forum.lottery.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.forum.lottery.application.MyApplication;
import com.forum.lottery.utils.AppConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/26.
 */
public class RxHttp {
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private Map<Class<?>,  Object> cacheServices;
    public static final String BASE_URL = "http://23.27.226.194/";
//    public static final String BASE_URL = "http://1685v1z125.iask.in:19658/";
//    public static final String BASE_URL = "http://zhy.xinwangpuhui.com/";
//    public static final String BASE_URL = "http://1541z56x30.51mypc.cn/";
//    public static final String BASE_URL = "http://192.168.10.167:8080/financialmall-web/";

    public RxHttp(){
        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(new EncryptInterceptor())
                .cookieJar(new CookiesManager())
                .addInterceptor(mTokenInterceptor)
//                .addInterceptor(new AddCookiesInterceptor())
//                .addInterceptor(new ReceivedCookiesInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .callFactory(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        cacheServices = new HashMap<>();
    }

    public <T> T create(final Class<T> service){
        T serviceApi = (T) cacheServices.get(service);
        if(serviceApi == null){
            serviceApi = retrofit.create(service);
            cacheServices.put(service, serviceApi);
        }
        return serviceApi;
    }

    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("content-Type", "application/json")
                    .build();
            return chain.proceed(request);
        }

    };

    /**
     * This interceptor put all the Cookies in Preferences in the Request.
     * Your implementation on how to get the Preferences MAY VARY.
     * <p>
     * Created by tsuharesu on 4/1/15.
     */
    public class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            HashSet<String> preferences = (HashSet<String>) MyApplication.getInstance().getSharedPreferences(AppConfig.FILE_NAME, Context.MODE_PRIVATE)
                    .getStringSet("cookie", new HashSet<String>());
            for (String cookie : preferences) {
                builder.addHeader("Cookie", "JSESSIONID=473D235F112C3A525DFE2856C3EA10C8; username=bill; userId=1");
            }

            return chain.proceed(builder.build());
        }
    }

    /**
     * This Interceptor add all received Cookies to the app DefaultPreferences.
     * Your implementation on how to save the Cookies on the Preferences MAY VARY.
     * <p>
     * Created by tsuharesu on 4/1/15.
     */
    public class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                SharedPreferences.Editor editor = MyApplication.getInstance()
                        .getSharedPreferences(AppConfig.FILE_NAME, Context.MODE_PRIVATE).edit();
                editor.putStringSet("cookie", cookies);

            }

            return originalResponse;
        }
    }

    /**
     * 给表单数据统一加上密钥
     */
    private static class EncryptInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(request.body() instanceof FormBody){
                Request.Builder newBuilder = request.newBuilder();
                FormBody.Builder newFormBuilder = new FormBody.Builder();
                FormBody oidFormBody = (FormBody) request.body();
                newBuilder.method(request.method(),newFormBuilder.build());
                return chain.proceed(newBuilder.build());
            }else{
                return chain.proceed(request);
            }
        }
    }
}
