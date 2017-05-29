package com.forum.lottery.entity;

import com.forum.lottery.model.BetRecordModel;
import com.forum.lottery.model.TrendModel;

import java.util.List;

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

    //走势图数据
    private String lotteryId;
    private List<TrendModel> records;

    //投注记录
    private int total;
    private List<BetRecordModel> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BetRecordModel> getBetRecords() {
        return rows;
    }

    public void setBetRecords(List<BetRecordModel> rows) {
        this.rows = rows;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public List<TrendModel> getRecords() {
        return records;
    }

    public void setRecords(List<TrendModel> records) {
        this.records = records;
    }

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
