package com.forum.lottery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 下注的详情
 * Created by Administrator on 2017/5/25 0025.
 */

public class BetDetailModel implements Serializable {

    private float unitPrice;
    private int buyCount;
    private String cpCategoryId;
    private String periodNO;    //当前期数
    private int playTypeId;
    private String playTypeName;
    private String buyNO;
    private String cpCategoryName;
    private String buyNoShow;

    private float peilv;    //赔率
    private float fanli;    //返利

    private String tab;  //lhc专用


    public String getTab(){
        return tab;
    }

    public void setTab(String tab){
        this.tab = tab;
    }

    public float getPeilv() {
        return peilv;
    }

    public void setPeilv(float peilv) {
        this.peilv = peilv;
    }

    public float getFanli() {
        return fanli;
    }

    public void setFanli(float fanli) {
        this.fanli = fanli;
    }

    public float getUnitPrice(){
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice){
        this.unitPrice = unitPrice;
    }

    public int getBuyCount(){
        return buyCount;
    }

    public void setBuyCount(int buyCount){
        this.buyCount = buyCount;
    }

    public String getCpCategoryId(){
        return cpCategoryId;
    }

    public void setCpCategoryId(String cpCategoryId){
        this.cpCategoryId = cpCategoryId;
    }

    public String getPeriodNO(){
        return periodNO;
    }

    public void setPeriodNO(String periodNO){
        this.periodNO = periodNO;
    }

    public int getPlayTypeId(){
        return playTypeId;
    }

    public void setPlayTypeId(int playTypeId){
        this.playTypeId = playTypeId;
    }

    public String getPlayTypeName(){
        return playTypeName;
    }

    public void setPlayTypeName(String playTypeName){
        this.playTypeName = playTypeName;
    }

    public String getBuyNO(){
        return buyNO;
    }

    public void setBuyNO(String buyNO){
        this.buyNO = buyNO;
    }

    public String getCpCategoryName(){
        return cpCategoryName;
    }

    public void setCpCategoryName(String cpCategoryName){
        this.cpCategoryName = cpCategoryName;
    }

    public String getBuyNoShow(){
        return buyNoShow;
    }

    public void setBuyNoShow(String buyNoShow){
        this.buyNoShow = buyNoShow;
    }

}
