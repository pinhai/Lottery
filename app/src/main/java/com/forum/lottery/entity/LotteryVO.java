package com.forum.lottery.entity;

/**
 * Created by Administrator on 2017/4/30.
 */

public class LotteryVO {
    private String issue;
    private int time;
    private String[] openNum;
    private String openTime;
    private String lotteryid;
    private String nextIssue;
    private String lotteryName;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String[] getOpenNum() {
        return openNum;
    }

    public void setOpenNum(String[] openNum) {
        this.openNum = openNum;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getLotteryid() {
        return lotteryid;
    }

    public void setLotteryid(String lotteryid) {
        this.lotteryid = lotteryid;
    }

    public String getNextIssue() {
        return nextIssue;
    }

    public void setNextIssue(String nextIssue) {
        this.nextIssue = nextIssue;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }
}
