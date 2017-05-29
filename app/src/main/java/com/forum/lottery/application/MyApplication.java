package com.forum.lottery.application;

import android.app.Application;
import android.content.Context;

import com.forum.lottery.utils.CrashHandler;
import com.forum.lottery.utils.LogUtils;
import com.forum.lottery.utils.ScreenUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Su on 2015/8/3.
 */
public class MyApplication extends Application {
    private static MyApplication application;
    public static MyApplication getInstance(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setDebug(true);
        application = this;
        initImageLoader(this);
        ScreenUtils.init(this);
        CrashHandler.getInstance().init(this);
    }


    public static void initImageLoader(Context context) {
        L.writeLogs(false);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new SSLImageDownloader(context))
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    private static class SSLImageDownloader extends BaseImageDownloader {

        public SSLImageDownloader(Context context) {
            super(context);
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra)
                throws IOException {
            HttpURLConnection connection = super.createConnection(url, extra);
            if(url.startsWith("https://")){
                return getSSLConnection(connection);
            }else{
                return connection;
            }
        }

        private HttpURLConnection getSSLConnection(HttpURLConnection connection){
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new X509TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
                ((HttpsURLConnection)connection).setSSLSocketFactory(sslContext.getSocketFactory());
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return connection;
        }

    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
}
