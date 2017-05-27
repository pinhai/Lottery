package com.forum.lottery.network;

import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.utils.AccountManager;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2016/10/31.
 */

public class CookiesManager implements CookieJar {
    private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
//                String cStr = item.toString();
//                if(cStr.contains("JSESSIONID") && !cStr.contains("username") && !cStr.contains("userId")){
//                    String value = item.value() + ",username=" + ",userId=";
//                }
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
//        if(AccountManager.getInstance().isLogin()){
//            UserVO user = AccountManager.getInstance().getUser();
//            for (Cookie item : cookies) {
//                String cStr = item.toString();
//                if(cStr.contains("JSESSIONID") && !cStr.contains("username") && !cStr.contains("userId")){
//                    String value = item.value() + ",username=" + user.getAccount() + ",userId=" + user.getId();
//                }
//            }
////            Cookie username = new Cookie.Builder()
////                    .name("username")
////                    .value(user.getAccount())
////                    .build();
////            Cookie userid = new Cookie.Builder()
////                    .name("userId")
////                    .value(user.getId())
////                    .build();
////            cookies.add(username);
////            cookies.add(userid);
//        }

        return cookies;
    }
}
