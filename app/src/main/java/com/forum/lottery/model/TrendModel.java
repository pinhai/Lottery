package com.forum.lottery.model;

/**
 * Created by admin_h on 2017/5/28.
 */

public class TrendModel {

    private String[] allcode;
    private String issue;//期数

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
}
