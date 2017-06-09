package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/9.
 */

public class BetBigBigModel2 {

    //第二种格式
    String title;
    List<BetBigItemModel> label;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BetBigItemModel> getLabel() {
        return label;
    }

    public void setLabel(List<BetBigItemModel> label) {
        this.label = label;
    }
}
