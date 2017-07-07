package com.forum.lottery.model;

/**
 * Created by admin on 2017/5/28.
 */

public class TrendModel {

    private String[] allcode;
    private String issue;//期数
    private String opentime;

    public String[] getAllcode() {
        return allcode;
    }

    public void setAllcode(String[] allcode) {
        this.allcode = allcode;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }
}
