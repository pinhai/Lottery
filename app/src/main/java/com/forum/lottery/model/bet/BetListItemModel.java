package com.forum.lottery.model.bet;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/5/24 0024.
 */

public class BetListItemModel implements Serializable{

    /**
     * title: '万位',
     no: '0|1|2|3|4|5|6|7|8|9',
     place: 0,
     cols: 1
     */

    private String title;
    private String no;
    private int place;
    private int cols;
    String methodid; //快3彩票每一个球对应一个玩法

    private List<BetItemModel> betItems;
    private List<String> methodidItems;

    public List<String> getMethodidItems() {
        return methodidItems;
    }

    public void setMethodidItems(List<String> methodidItems) {
        this.methodidItems = methodidItems;
    }

    public String getMethodid() {
        return methodid;
    }

    public void setMethodid(String methodid) {
        this.methodid = methodid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public List<BetItemModel> getBetItems(){
        return betItems;
    }

    public void setBetItems(List<BetItemModel> betItems){
        this.betItems = betItems;
    }
}
