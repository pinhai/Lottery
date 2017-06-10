package com.forum.lottery.model.bet;

/**
 * 下注选项
 * Created by admin_h on 2017/5/24.
 */

public class BetItemModel {

    private String name;
    private boolean checked = false;

    private float prize = -1;

    public BetItemModel(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }

    public BetItemModel(String name, boolean checked, float prize){
        this.name = name;
        this.checked = checked;
        this.prize = prize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}