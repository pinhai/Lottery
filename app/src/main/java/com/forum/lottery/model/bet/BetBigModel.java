package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/5.
 */

public class BetBigModel {

    String gtitle;
//    BetBigItemModel[] label;
    List<BetBigItemModel> label;

    public String getGtitle() {
        return gtitle;
    }

    public void setGtitle(String gtitle) {
        this.gtitle = gtitle;
    }

    public List<BetBigItemModel> getLabel() {
        return label;
    }

    public void setLabel(List<BetBigItemModel> label) {
        this.label = label;
    }
}
