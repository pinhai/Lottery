package com.forum.lottery.model.bet.lhc;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class BetLHCDataModel {

    String subtitle;
    List<BetLHCSubdataModel> subdata;

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
