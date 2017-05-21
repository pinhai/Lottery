package com.forum.lottery.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/17.
 */

public class RegisterResult {

    /**
     * Result : true
     * userId : 4
     * userName : chenhanfeng
     * Code : 0
     * Desc : 注册成功！
     */
    @SerializedName("Result")
    private boolean result;
    private int userId;
    private String userName;
    @SerializedName("Code")
    private String code;
    @SerializedName("Desc")
    private String desc;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String Code) {
        this.code = Code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
