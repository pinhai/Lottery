package com.forum.lottery.model.bet;

import java.io.Serializable;

/**
 * 下注选项
 * Created by admin_h on 2017/5/24.
 */

public class BetItemModel implements Serializable{

    private String name;
    private boolean checked = false;

    private float prize = -1;
    String methodId;

    public BetItemModel(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }

    public BetItemModel(String name, boolean checked, float prize){
        this.name = name;
        this.checked = checked;
        this.prize = prize;
    }

    public BetItemModel(String name, boolean checked, float prize, String methodId){
        this.name = name;
        this.checked = checked;
        this.prize = prize;
        this.methodId = methodId;
    }


    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
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
