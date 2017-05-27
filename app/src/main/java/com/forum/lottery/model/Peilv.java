package com.forum.lottery.model;

/**
 * Created by admin_h on 2017/5/27.
 */

public class Peilv {

//    [{"methodid":37,"name":"定位胆_定位胆","bonusProp":9.8,"caipiaoid":51}]

    private int caipiaoid;
    private int methodid;
    private String name;
    private float bonusProp;    //赔率

    public int getCaipiaoid() {
        return caipiaoid;
    }

    public void setCaipiaoid(int caipiaoid) {
        this.caipiaoid = caipiaoid;
    }

    public int getMethodid() {
        return methodid;
    }

    public void setMethodid(int methodid) {
        this.methodid = methodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBonusProp() {
        return bonusProp;
    }

    public void setBonusProp(float bonusProp) {
        this.bonusProp = bonusProp;
    }
}
