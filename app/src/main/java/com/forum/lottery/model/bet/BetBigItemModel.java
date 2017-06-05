package com.forum.lottery.model.bet;

import java.util.List;

/**
 * Created by admin_h on 2017/6/5.
 */

public class BetBigItemModel {

    List<BetSelectAreaModel> selectarea;
    String show_str;
    String code_sp;
    int methodid;
    String name;
    String methoddesc;
    String methodhelp;
    String methodexample;
    int maxcodecount;
    int isrx;
    int numcount;
    String defaultposition;
    String desc;

    public List<BetSelectAreaModel> getSelectarea() {
        return selectarea;
    }

    public void setSelectarea(List<BetSelectAreaModel> selectarea) {
        this.selectarea = selectarea;
    }

    public String getShow_str() {
        return show_str;
    }

    public void setShow_str(String show_str) {
        this.show_str = show_str;
    }

    public String getCode_sp() {
        return code_sp;
    }

    public void setCode_sp(String code_sp) {
        this.code_sp = code_sp;
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

    public String getMethoddesc() {
        return methoddesc;
    }

    public void setMethoddesc(String methoddesc) {
        this.methoddesc = methoddesc;
    }

    public String getMethodhelp() {
        return methodhelp;
    }

    public void setMethodhelp(String methodhelp) {
        this.methodhelp = methodhelp;
    }

    public String getMethodexample() {
        return methodexample;
    }

    public void setMethodexample(String methodexample) {
        this.methodexample = methodexample;
    }

    public int getMaxcodecount() {
        return maxcodecount;
    }

    public void setMaxcodecount(int maxcodecount) {
        this.maxcodecount = maxcodecount;
    }

    public int getIsrx() {
        return isrx;
    }

    public void setIsrx(int isrx) {
        this.isrx = isrx;
    }

    public int getNumcount() {
        return numcount;
    }

    public void setNumcount(int numcount) {
        this.numcount = numcount;
    }

    public String getDefaultposition() {
        return defaultposition;
    }

    public void setDefaultposition(String defaultposition) {
        this.defaultposition = defaultposition;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
