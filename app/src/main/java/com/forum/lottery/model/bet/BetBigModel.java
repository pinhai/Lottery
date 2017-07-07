package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */

public class BetBigModel {

    String gtitle;
//    BetBigItemModel[] label;
    List<BetBigItemModel> label;


    //第三种格式
    BetBigLayout layout;
    int method;
    float prize;


    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public String getGtitle() {
        return gtitle;
    }

    public void setGtitle(String gtitle) {
        this.gtitle = gtitle;
    }

    public List<BetBigItemModel> getLabel() {
        return label;
    }

    public void setLabel(List<BetBigItemModel> label) {
        this.label = label;
    }

    public BetBigLayout getLayout() {
        return layout;
    }

    public void setLayout(BetBigLayout layout) {
        this.layout = layout;
    }



    public class BetBigLayout{
        String no;
        int place;

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
    }
}
