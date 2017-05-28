package com.forum.lottery.entity;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ResultData<T> {

    /**
     * action :
     * data : JMDM
     * errorCode : 0
     * errorMsg : 成功
     */

    private T data;
    private int status;
    private String msg;

    private int code;
    private String desc;
    private String result;  //获取机选的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
