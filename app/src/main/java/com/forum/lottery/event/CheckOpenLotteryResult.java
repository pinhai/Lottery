package com.forum.lottery.event;

import com.forum.lottery.model.NextIssueModel;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class CheckOpenLotteryResult {

//    public String issue;
//    public String nextIssue;
//    public String[] openNum;
//    public String openTime;

    public NextIssueModel value;

    public CheckOpenLotteryResult(NextIssueModel value){
        this.value = value;
    }

}
