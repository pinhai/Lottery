package com.forum.lottery.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/25.
 */

public class PlayTypeB implements Serializable {

    private String playTypeB;
    private String playId;
    private String peilv;

    public String getPlayTypeB() {
        return playTypeB;
    }

    public void setPlayTypeB(String playTypeB) {
        this.playTypeB = playTypeB;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPeilv() {
        return peilv;
    }

    public void setPeilv(String peilv) {
        this.peilv = peilv;
    }
}
