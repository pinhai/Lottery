package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin on 2017/6/10.
 */

public class BetBigItemModel41 {
    @Deprecated
    BetItemModel41 layout;

    String no;
    String method;
    String prize;

    private List<BetItemModel> betItems;
    private List<String> methods;
    private List<String> prizes;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public List<String> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<String> prizes) {
        this.prizes = prizes;
    }

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
