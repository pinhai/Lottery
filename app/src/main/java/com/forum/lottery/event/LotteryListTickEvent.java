package com.forum.lottery.event;

import com.forum.lottery.entity.LotteryVO;

import java.util.List;

/**
 * Created by admin_h on 2017/5/28.
 */

public class LotteryListTickEvent {

    public List<LotteryVO> data;

    public LotteryListTickEvent(List<LotteryVO> data){
        this.data = data;
    }

}
