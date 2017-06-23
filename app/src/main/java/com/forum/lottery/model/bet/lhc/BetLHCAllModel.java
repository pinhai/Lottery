package com.forum.lottery.model.bet.lhc;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class BetLHCAllModel {

    List<BetLHCDataModel> data;
    String title;

    public List<BetLHCDataModel> getDatas(){
        return data;
    }

    public void setDatas(List<BetLHCDataModel> datas){
        this.data = datas;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
