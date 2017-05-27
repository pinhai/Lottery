package com.forum.lottery.model;

import java.util.List;

/**
 * 下彩提交
 * Created by admin_h on 2017/5/28.
 */

public class BetSubmitModel {

    private List<BetDetailModel> buycpVos;

    public BetSubmitModel(List<BetDetailModel> buycpVos){
        this.buycpVos = buycpVos;
    }

    public List<BetDetailModel> getBuycpVos() {
        return buycpVos;
    }

    public void setBuycpVos(List<BetDetailModel> buycpVos) {
        this.buycpVos = buycpVos;
    }
}
