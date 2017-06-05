package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/5.
 */

public class BetSelectAreaModel {

    private String type;
    private BetListItemModel[] layout;
    private int noBigIndex;
    private boolean isButton;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BetListItemModel[] getLayout() {
        return layout;
    }

    public void setLayout(BetListItemModel[] layout) {
        this.layout = layout;
    }

    public int getNoBigIndex() {
        return noBigIndex;
    }

    public void setNoBigIndex(int noBigIndex) {
        this.noBigIndex = noBigIndex;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }
}
