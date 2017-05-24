package com.forum.lottery.utils;

import com.forum.lottery.model.BetItemModel;
import com.forum.lottery.model.BetListItemModel;

import java.util.ArrayList;
import java.util.List;

import rx.subjects.ReplaySubject;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class LotteryUtils {

    /**
     * 获取下注的注数-加法
     * @param data
     * @return
     */
    public static int getBetCountFromAddition(List<BetListItemModel> data){
        int count = 0;
//        int[] counts = new int[data.size()];
        for(int i=0; i<data.size(); i++){
            List<BetItemModel> listitem = data.get(i).getBetItems();
            for(int j=0; j<listitem.size(); j++){
                if(listitem.get(j).isChecked()){
                    ++count;
                }
            }
        }

        return count;
    }

    /**
     * 获取下注的彩票-加法
     * @param data
     * @return
     */
    public static String getBetLotteryFromAddition(List<BetListItemModel> data){
//        List<String> betLottery = new ArrayList<>();
        String betLottery = "";
        for(int i=0; i<data.size(); i++){
            List<BetItemModel> listitem = data.get(i).getBetItems();
            for(int j=0; j<listitem.size(); j++){
                if(listitem.get(j).isChecked()){
                    String value = listitem.get(j).getName();
//                    if(j != listitem.size()-1){
                    betLottery = betLottery + getBetItemForAddition(data.get(i).getLabel(), value) + "&";
//                    }
                }
            }
        }
        betLottery = betLottery.substring(0, betLottery.length()-1);
        return betLottery;
    }

    /**
     * 获取下注的注数-乘法
     * @param data
     * @return
     */
    public static int getBetCountFromMulti(List<BetListItemModel> data){
        int count = 0;
        int[] counts = new int[data.size()];
        for(int i=0; i<data.size(); i++){
            List<BetItemModel> listitem = data.get(i).getBetItems();
            for(int j=0; j<listitem.size(); j++){
                if(listitem.get(j).isChecked()){
                    ++counts[i];
                }
            }

        }
        for(int k=0; k < counts.length; k++){
            if(k == 0){
                count = counts[k];
            }else{
                count = count * counts[k];
            }
        }

        return count;
    }

    private static List<List<String[]>> betLotterys;
    /**
     * 获取下注的彩票-乘法
     * @param data
     * @return
     */
    public static List<String[]> getBetLotteryFromMulti(List<BetListItemModel> data){
        betLotterys = new ArrayList<>();
//        int count = getBetCount(data);
        List<String[]> betsEveryRow = new ArrayList<>();//每一行选的值
        for(int i=0; i<data.size(); i++){
            List<BetItemModel> listitem = data.get(i).getBetItems();
            List<String> checked = new ArrayList<>();
            for(int j=0; j<listitem.size(); j++) {
                if (listitem.get(j).isChecked()) {
                    checked.add(listitem.get(j).getName());
                }
            }
            String[] betEveryRowItem = new String[checked.size()];
            for(int k=0; k<checked.size(); k++){
                betEveryRowItem[k] = checked.get(k);
            }
            betsEveryRow.add(betEveryRowItem);

        }

        doExchange(betsEveryRow);

        return betLotterys.get(0);
    }

    private static void doExchange(List arrayLists){

        int len=arrayLists.size();
        //判断数组size是否小于2，如果小于说明已经递归完成了，否则你们懂得的，不懂？断续看代码
        if (len<2){
            LotteryUtils.betLotterys = arrayLists;
            return;
        }
        //拿到第一个数组
        int len0;
        if (arrayLists.get(0) instanceof String[]){
            String[] arr0= (String[]) arrayLists.get(0);
            len0=arr0.length;
        }else {
            len0=((ArrayList<String>)arrayLists.get(0)).size();
        }

        //拿到第二个数组
        String[] arr1= (String[]) arrayLists.get(1);
        int len1=arr1.length;

        //计算当前两个数组一共能够组成多少个组合
        int lenBoth=len0*len1;

        //定义临时存放排列数据的集合
        ArrayList<ArrayList<String>> tempArrayLists=new ArrayList<>(lenBoth);

        //第一层for就是循环arrayLists第一个元素的
        for (int i=0;i<len0;i++){
            //第二层for就是循环arrayLists第二个元素的
            for (int j=0;j<len1;j++){
                //判断第一个元素如果是数组说明，循环才刚开始
                if (arrayLists.get(0) instanceof String[]){
                    String[] arr0= (String[]) arrayLists.get(0);
                    ArrayList<String> arr=new ArrayList<>();
                    arr.add(arr0[i]);
                    arr.add(arr1[j]);
                    //把排列数据加到临时的集合中
                    tempArrayLists.add(arr);
                }else {
                    //到这里就明循环了最少一轮啦，我们把上一轮的结果拿出来继续跟arrayLists的下一个元素排列
                    ArrayList<ArrayList<String>> arrtemp= (ArrayList<ArrayList<String>>) arrayLists.get(0);
                    ArrayList<String> arr=new ArrayList<>();
                    for (int k=0;k<arrtemp.get(i).size();k++){
                        arr.add(arrtemp.get(i).get(k));
                    }
                    arr.add(arr1[j]);
                    tempArrayLists.add(arr);
                }
            }
        }

        //这是根据上面排列的结果重新生成的一个集合
        List newArrayLists=new ArrayList<>();
        //把还没排列的数组装进来，看清楚i=2的喔，因为前面两个数组已经完事了，不需要再加进来了
        for (int i=2;i<arrayLists.size();i++){
            newArrayLists.add(arrayLists.get(i));
        }
        //记得把我们辛苦排列的数据加到新集合的第一位喔，不然白忙了
        newArrayLists.add(0,tempArrayLists);

        //你没看错，我们这整个算法用到的就是递归的思想。
        doExchange(newArrayLists);
    }

    private static String getBetItemForAddition(String label, String value){
        String result = "";
        if(label == "万位"){
            result = value + "||||";
        }else if(label == "千位"){
            result = "|" + value + "|||";
        }else if(label == "百位"){
            result = "||" + value + "||";
        }else if(label == "十位"){
            result = "|||" + value + "|";
        }else if(label == "个位"){
            result = value;
        }

        return result;
    }


    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}