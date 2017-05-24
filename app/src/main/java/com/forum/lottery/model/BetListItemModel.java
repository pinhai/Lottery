package com.forum.lottery.model;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/5/24 0024.
 */

public class BetListItemModel {

    private String label;
    private List<BetItemModel> betItems;

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public List<BetItemModel> getBetItems(){
        return betItems;
    }

    public void setBetItems(List<BetItemModel> betItems){
        this.betItems = betItems;
    }
}
