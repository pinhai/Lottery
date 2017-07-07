package com.forum.lottery.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/30.
 */

public class BankInfoModel implements Serializable{

    /**
     * {"bankInfo":{"truename":null,"bankname":null,"bankno":null,"province":null,
     * "city":null,"transPwd":null,"userId":1,"username":"bill","buyCpAmt":4.0,
     * "createTime":null,"updateTime":null}
     */

    private String truename;
    private String bankname;
    private String bankno;
    private String province;
    private String city;
    private String transPwd;
    private String userId;
    private String username;
    private float buyCpAmt;
    private String createTime;
    private String updateTime;

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTransPwd() {
        return transPwd;
    }

    public void setTransPwd(String transPwd) {
        this.transPwd = transPwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getBuyCpAmt() {
        return buyCpAmt;
    }

    public void setBuyCpAmt(float buyCpAmt) {
        this.buyCpAmt = buyCpAmt;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
