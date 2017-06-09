package com.forum.lottery.model.bet;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/5/24 0024.
 */

public class BetListItemModel {

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
    private List<BetItemModel> betItems;


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
