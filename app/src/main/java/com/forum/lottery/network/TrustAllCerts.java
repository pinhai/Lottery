package com.forum.lottery.network;

import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class TrustAllCerts implements X509TrustManager {

    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException{

    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException{

    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers(){
        return new java.security.cert.X509Certificate[0];
    }
}
