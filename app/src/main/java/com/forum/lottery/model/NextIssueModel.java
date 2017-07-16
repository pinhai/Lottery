package com.forum.lottery.model;

import java.util.List;

/**
 * Created by admin_h on 2017/7/16.
 */

public class NextIssueModel {

    //下一期开奖结果
    String issue;
    List<String> code;
    String opentime;
    List<TrendModel> recentlycode;

    public List<TrendModel> getRecentlycode() {
        return recentlycode;
    }

    public void setRecentlycode(List<TrendModel> recentlycode) {
        this.recentlycode = recentlycode;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }
}
