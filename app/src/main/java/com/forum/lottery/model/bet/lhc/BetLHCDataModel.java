package com.forum.lottery.model.bet.lhc;

import com.forum.lottery.model.bet.BetItemModel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class BetLHCDataModel {

    String subtitle;
    List<BetLHCSubdataModel> subdata;

    private List<BetItemModel> betItems;
    private List<String> methodids;


    public List<BetLHCSubdataModel> getSubdata() {
        return subdata;
    }

    public void setSubdata(List<BetLHCSubdataModel> subdata) {
        this.subdata = subdata;
    }

    public List<BetItemModel> getBetItems() {
        return betItems;
    }

    public void setBetItems(List<BetItemModel> betItems) {
        this.betItems = betItems;
    }

    public List<String> getMethodids() {
        return methodids;
    }

    public void setMethodids(List<String> methodids) {
        this.methodids = methodids;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }

    public List<BetLHCSubdataModel> getSubdatas(){
        return subdata;
    }

    public void setSubdatas(List<BetLHCSubdataModel> subdatas){
        this.subdata = subdatas;
    }
}
