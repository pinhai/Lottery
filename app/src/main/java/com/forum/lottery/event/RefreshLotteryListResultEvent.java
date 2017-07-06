package com.forum.lottery.event;

import com.forum.lottery.entity.LotteryVO;

import java.util.List;

/**
 * Created by admin_h on 2017/5/29.
 */

public class RefreshLotteryListResultEvent {
    public List<LotteryVO> data;

    public RefreshLotteryListResultEvent(List<LotteryVO> data){
        this.data = data;
    }
}
