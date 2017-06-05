package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/5.
 */

public class BetBigBigModel {

    String isrx;
    String isdefault;
    String title;
    BetBigModel[] label;

    public String getIsrx() {
        return isrx;
    }

    public void setIsrx(String isrx) {
        this.isrx = isrx;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BetBigModel[] getLabel() {
        return label;
    }

    public void setLabel(BetBigModel[] label) {
        this.label = label;
    }
}
