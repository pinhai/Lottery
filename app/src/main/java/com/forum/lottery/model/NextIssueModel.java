package com.forum.lottery.model;

import java.util.List;

/**
 * Created by admin_h on 2017/7/16.
 */

public class NextIssueModel {

    /*{"recentlycode":[{"allcode":["7","1","5","8","7"],"issue":"20170717060","opentime":"2017-07-17 16:00:51"},
        {"allcode":["1","8","1","0","6"],"issue":"20170717059","opentime":"2017-07-17 15:50:45"},
        {"allcode":["8","6","1","7","8"],"issue":"20170717058","opentime":"2017-07-17 15:40:51"},
        {"allcode":["8","8","6","0","9"],"issue":"20170717057","opentime":"2017-07-17 15:30:44"},
        {"allcode":["4","0","8","3","5"],"issue":"20170717056","opentime":"2017-07-17 15:20:50"}],
        "issue":null,"code":["7","1","5","8","7"],"opentime":"2017-07-17 16:19:25"}*/

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
