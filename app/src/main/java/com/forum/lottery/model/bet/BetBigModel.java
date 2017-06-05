package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/5.
 */

public class BetBigModel {

    String gtitle;
    BetBigItemModel[] label;

    public String getGtitle() {
        return gtitle;
    }

    public void setGtitle(String gtitle) {
        this.gtitle = gtitle;
    }

    public BetBigItemModel[] getLabel() {
        return label;
    }

    public void setLabel(BetBigItemModel[] label) {
        this.label = label;
    }
}
