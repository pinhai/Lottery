package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/5.
 */

public class BetSelectAreaModel {

    private String type;
//    private BetListItemModel[] layout;
    List<BetListItemModel> layout;
    private int noBigIndex;
    private boolean isButton;
    private boolean selPosition;


    public boolean isSelPosition() {
        return selPosition;
    }

    public void setSelPosition(boolean selPosition) {
        this.selPosition = selPosition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BetListItemModel> getLayout() {
        return layout;
    }

    public void setLayout(List<BetListItemModel> layout) {
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
