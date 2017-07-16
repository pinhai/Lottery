package com.forum.lottery.event;

/**
 * Created by admin_h on 2017/7/16.
 */

public class OpeningLotteryEvent {

    public String lotteryId;
    public String play;

    public OpeningLotteryEvent(String lotteryId, String play){
        this.lotteryId = lotteryId;
        this.play = play;
    }

}
