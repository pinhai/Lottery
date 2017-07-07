package com.forum.lottery.model.bet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/9.
 */

public class BetListModel {

    private ArrayList<BetListItemModel> data = new ArrayList<>();
    boolean selPosition;

    public boolean isSelPosition() {
        return selPosition;
    }

    public void setSelPosition(boolean selPosition) {
        this.selPosition = selPosition;
    }

    public List<BetListItemModel> getData() {
        return data;
    }

    public void setData(List<BetListItemModel> data) {
        this.data = (ArrayList<BetListItemModel>) data;
    }
}
