package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/10.
 */

public class BetBigItemModel41 {

    BetItemModel41 layout;
    int method;
    float prize;

    private List<BetItemModel> betItems;

    public List<BetItemModel> getBetItems() {
        return betItems;
    }

    public void setBetItems(List<BetItemModel> betItems) {
        this.betItems = betItems;
    }

    public BetItemModel41 getLayout() {
        return layout;
    }

    public void setLayout(BetItemModel41 layout) {
        this.layout = layout;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }
}
