package com.forum.lottery.model.bet.lhc;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class BetLHCSubdataModel {

    int methodid;
    String name;
    float prizeMax;

    public int getMethodid(){
        return methodid;
    }

    public void setMethodid(int methodid){
        this.methodid = methodid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public float getPrizeMax(){
        return prizeMax;
    }

    public void setPrizeMax(float prizeMax){
        this.prizeMax = prizeMax;
    }
}
