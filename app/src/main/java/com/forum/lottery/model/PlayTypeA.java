package com.forum.lottery.model;

import java.util.List;

/**
 * Created by admin_h on 2017/5/25.
 */

public class PlayTypeA {

    private String lotteryId;
    private String playTypeA;
    List<PlayTypeB> playTypeBs;

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPlayTypeA() {
        return playTypeA;
    }

    public void setPlayTypeA(String playTypeA) {
        this.playTypeA = playTypeA;
    }

    public List<PlayTypeB> getPlayTypeBs() {
        return playTypeBs;
    }

    public void setPlayTypeBs(List<PlayTypeB> playTypeBs) {
        this.playTypeBs = playTypeBs;
    }
}
