package com.forum.lottery.utils;

import android.content.Context;

import com.forum.lottery.R;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.model.bet.BetBigBigModel;
import com.forum.lottery.model.bet.BetBigItemModel;
import com.forum.lottery.model.bet.BetBigModel;
import com.forum.lottery.model.bet.BetItemModel;
import com.forum.lottery.model.bet.BetListItemModel;
import com.forum.lottery.model.PlayTypeA;
import com.forum.lottery.model.PlayTypeB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 获取下注的彩票， 比如：|||1|
     * @param data
     * @return
     */
    public static String getBetLottery(List<BetListItemModel> data){
//        List<String> betLottery = new ArrayList<>();
        String betLottery = "";

        for(int i=0; i<data.size(); i++){
            List<BetItemModel> listitem = data.get(i).getBetItems();
            for(int j=0; j<listitem.size(); j++){
                if(listitem.get(j).isChecked()){
                    betLottery += listitem.get(j).getName() + "_";
                }
            }
            if(betLottery.endsWith("_")){
                betLottery = betLottery.substring(0, betLottery.length()-1);
            }
            if(i != data.size()-1){
                betLottery += "|";
            }
        }

//        for(int i=0; i<data.size(); i++){
//            List<BetItemModel> listitem = data.get(i).getBetItems();
//            for(int j=0; j<listitem.size(); j++){
//                if(listitem.get(j).isChecked()){
//                    String value = listitem.get(j).getName();
////                    if(j != listitem.size()-1){
//                    betLottery = betLottery + getBetItemForAddition(data.get(i).getTitle(), value) + "&";
////                    }
//                }
//            }
//        }
//        betLottery = betLottery.substring(0, betLottery.length()-1);
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


    /**
     * 秒转换成xx:xx:xx
     * @param time
     * @return
     */
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

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 机选-加法
     * @param data
     */
    public static void selectByMachineFromAddition(List<BetListItemModel> data){
        boolean run = true;
        int total = 0;
        for(int i=0; i<data.size(); i++){
            total += data.get(i).getBetItems().size();
        }
        int index = 0;
        retry:
        while(run){
            Random r = new Random();
            int random = r.nextInt(total - 1);
            for(int i = 0; i < data.size(); i++){
                List<BetItemModel> items = data.get(i).getBetItems();
                for(int j = 0; j < items.size(); j++){
                    if(random == index){
                        if(!items.get(j).isChecked()){
                            items.get(j).setChecked(true);
                            run = false;
                        }else{
                            break retry;
                        }
                    }
                    ++index;
                }
            }
        }
    }

    /**
     * 机选-加法-在最终下注界面机选
     */
    public static BetDetailModel selectByMachineFromAddition2(BetDetailModel temp){
        BetDetailModel result = new BetDetailModel();
        result.setPlayTypeName(temp.getPlayTypeName());
        result.setUnitPrice(2);
        result.setBuyCount(1);
        result.setCpCategoryId(temp.getCpCategoryId());
        result.setPeriodNO(temp.getPeriodNO());
        result.setPlayTypeId(temp.getPlayTypeId());
        result.setPlayTypeName(temp.getPlayTypeName());
        String[] buyNos = temp.getBuyNO().split("&");
        Random r = new Random();
        int total = 10;
        int random = r.nextInt(total - 1);
        int weishu = r.nextInt(4);
        String buyNo = "";
        for(int i=0; i<weishu; i++){
            buyNo += "|";
        }
        buyNo += random;
        for(int j=0; j<4-weishu; j++){
            buyNo += "|";
        }
        result.setBuyNO(buyNo);
        result.setCpCategoryName(temp.getCpCategoryName());
        result.setPeilv(temp.getPeilv());
        result.setFanli(temp.getFanli());

        return result;
    }

    /**
     * 通过后台服务器-机选
     * @param data
     * @param result
     */
    public static void selectByRemote(List<BetListItemModel> data, String result) {
        String[] temps = result.split("\\|");
        for(int i=0; i<temps.length; i++){
            List<BetItemModel> betItemModels = data.get(i).getBetItems();
            String temp = temps[i];
            for(BetItemModel item : betItemModels){
                if(temp.equals(item.getName())){
                    item.setChecked(true);
                }
            }
        }
    }

    /**
     * 得到已下注的详情-大注-某种玩法下的所有注数
     * @param data
     * @param lotteryVO
     * @param betCount
     * @param oneBetMoney
     * @return
     */
    public static List<BetDetailModel> getBettedLottery(List<BetListItemModel> data, LotteryVO lotteryVO, int betCount,
                                                        float oneBetMoney, float peilv, float fanli,
                                                        String playId, String playName){
        List<BetDetailModel> result = new ArrayList<>();
        BetDetailModel item = new BetDetailModel();
        item.setBuyNoShow(getBetLottery(data));
        item.setBuyCount(betCount);
        item.setBuyNO(item.getBuyNoShow());
        item.setCpCategoryName(lotteryVO.getLotteryName());
        item.setCpCategoryId(lotteryVO.getLotteryid());
        item.setUnitPrice(oneBetMoney);
        item.setPeriodNO(lotteryVO.getNextIssue());
        item.setPeilv(peilv);
        item.setFanli(fanli);
        item.setPlayTypeId(Integer.parseInt(playId));
//        item.setPlayTypeName("[定位胆_定位胆]");
        item.setPlayTypeName(playName);
        result.add(item);

        return result;
    }

    /**
     * 获取某彩票所有玩法
     * @param lotteryId
     */
    public static List<PlayTypeA> getPlayType(Context ctx, String lotteryId){
        List<PlayTypeA> result = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(ctx.getResources().getAssets().open("wanfapeilv.txt") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            List<String[]> temp = new ArrayList<>();
            while((line = bufReader.readLine()) != null) {
//                line = line.trim();
                line = line.substring(1, line.length() - 1);
                String[] lineItem = line.split("', '");
                if(lineItem[2].equals(lotteryId)){
                    temp.add(lineItem);
                }
            }

            for(int i=0; i<temp.size(); i++){
                String[] data = temp.get(i);

                PlayTypeA a;

                String[] playType = data[1].split("_");
                PlayTypeB b = new PlayTypeB();
                b.setPlayTypeB(playType[1]);
                b.setPlayId(data[0]);
                b.setPeilv(data[3]);

                boolean hasA = false;
                for(int j=0; j<result.size(); j++){
                    if(playType[0].equals(result.get(j).getPlayTypeA())){
                        hasA = true;
                        List<PlayTypeB> bTemp = result.get(j).getPlayTypeBs();
                        if(bTemp == null || bTemp.size() == 0){
                            bTemp = new ArrayList<>();
                            bTemp.add(b);
                            result.get(j).setPlayTypeBs(bTemp);
                        }else{
                            bTemp.add(b);
                        }
                    }
                }
                if(!hasA){
                    a = new PlayTypeA();
                    a.setLotteryId(lotteryId);
                    a.setPlayTypeA(playType[0]);
                    List<PlayTypeB> typeBs = new ArrayList<>();
                    typeBs.add(b);
                    a.setPlayTypeBs(typeBs);
                    result.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encode(String betLottery) {
        String result = "";
        for(char c : betLottery.toCharArray()){
            if(c == '|'){
                result += "%7C";
            }else{
                result += c;
            }
        }

        return result;
    }

    /**
     *
     * @param count
     */
    public static List<String> getTrendWeishuMenu(Context context, int count) {
        String[] temp ;
        if(count == 10){
            temp = context.getResources().getStringArray(R.array.weishu_10);
        }else if(count == 7){
            temp = context.getResources().getStringArray(R.array.weishu_7);
        }else if(count == 5){
            temp = context.getResources().getStringArray(R.array.weishu_5);
        }else if(count == 4){
            temp = context.getResources().getStringArray(R.array.weishu_4);
        }else if(count == 3){
            temp = context.getResources().getStringArray(R.array.weishu_3);
        }else{
            temp = new String[0];
        }

        return Arrays.asList(temp);
    }

    /**
     * 获取某彩种下注界面数据
     * @param context
     * @param lotteryId
     * @param playWays
     * @return
     */
    public static List<BetBigBigModel> getBetLayout(Context context, String lotteryId, List<PlayTypeA> playWays){
        List<BetBigBigModel> result = new ArrayList<>();
        String fileName = "";

        List<String> playTypeAsMatch = new ArrayList<>(), playTypeBsMatch = new ArrayList<>();

        if(lotteryId.equals("51") || lotteryId.equals("7") || lotteryId.equals("4") || lotteryId.equals("73")){
            fileName = "face.5.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json5));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json5));
        }else if(lotteryId.equals("2")){
            fileName = "face.12.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json12));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json12));
        }else if(lotteryId.equals("1")){
            fileName = "face.11.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json12));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json12));
        }else if(lotteryId.equals("3")){
            fileName = "face.4.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json12));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json12));
        }else if(lotteryId.equals("12") ||lotteryId.equals("13") ||lotteryId.equals("14") ||lotteryId.equals("15")){
            fileName = "face.8.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json8));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json8));
        }else if(lotteryId.equals("11")){
            fileName = "face.7.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json7));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json7));
        }else if(lotteryId.equals("9") || lotteryId.equals("52")){
            fileName = "face.3.json";
            playTypeAsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeA_json3));
            playTypeBsMatch = Arrays.asList(context.getResources().getStringArray(R.array.playTypeB_json3));
        }else if(lotteryId.equals("41") || lotteryId.equals("42")){
//            fileName = "methods.41.json";
        }

        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            result = jsonToArrayList(replaceBlank(sb.toString()), BetBigBigModel.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //删除冗余数据
        for(int i=0; i < result.size(); i++){
            BetBigBigModel betBigBigModel = result.get(i);
            if(betBigBigModel == null){
                result.remove(i);
                i--;
                continue;
            }
            for(int j=0; j<betBigBigModel.getLabel().size(); j++){
                BetBigModel betBigModel = betBigBigModel.getLabel().get(j);
                if(betBigModel == null || betBigModel.getLabel() == null){
                    betBigBigModel.getLabel().remove(j);
                    j--;
                    continue;
                }
                for(int k=0; k<betBigModel.getLabel().size(); k++){
                    BetBigItemModel betBigItemModel = betBigModel.getLabel().get(k);
                    if(betBigItemModel == null || betBigItemModel.getSelectarea() == null
                            || betBigItemModel.getSelectarea().getLayout() == null){
                        betBigModel.getLabel().remove(k);
                        k--;
                        continue;
                    }
                    for(int m=0; m<betBigItemModel.getSelectarea().getLayout().size(); m++){
                        BetListItemModel betListItemModel = betBigItemModel.getSelectarea().getLayout().get(m);
                        if(betListItemModel == null){
                            betBigItemModel.getSelectarea().getLayout().remove(m);
                            m--;
                            continue;
                        }
                    }
                }
            }
        }

        //生成玩法对象
        for(BetBigBigModel betBigBigModel : result){
            if(!playTypeAsMatch.contains(betBigBigModel.getTitle())){
                continue;
            }
            PlayTypeA playTypeA = new PlayTypeA();
            playTypeA.setLotteryId(lotteryId);
            playTypeA.setPlayTypeA(betBigBigModel.getTitle());
            List<PlayTypeB> playTypeBs = new ArrayList<>();

            for(BetBigModel betBigModel : betBigBigModel.getLabel()){
                for(BetBigItemModel betBigItemModel : betBigModel.getLabel()){
                    if(betBigItemModel != null && betBigItemModel.getSelectarea() != null && betBigItemModel.getSelectarea().getLayout() != null
                            && playTypeBsMatch.contains(betBigItemModel.getDesc())){
                        PlayTypeB playTypeB = new PlayTypeB();
                        playTypeB.setPlayTypeB(betBigItemModel.getDesc());
                        playTypeB.setPlayId(betBigItemModel.getMethodid()+"");
                        playTypeB.setPeilv(9.8+"");
                        if(checkPlayWay(lotteryId, playTypeA.getPlayTypeA(), playTypeB.getPlayTypeB())){
                            playTypeBs.add(playTypeB);
                        }

                        for(BetListItemModel betListItemModel : betBigItemModel.getSelectarea().getLayout()){
                            String no = betListItemModel.getNo();
                            String[] nos =  no.split("\\|");
                            List<BetItemModel> betItemModels = new ArrayList<>();
                            for(String n : nos){
                                BetItemModel item = new BetItemModel(n, false);
                                betItemModels.add(item);
                            }
                            betListItemModel.setBetItems(betItemModels);
                        }
                    }
                }
            }
            if(playTypeBs.size() > 0){
                playTypeA.setPlayTypeBs(playTypeBs);
                playWays.add(playTypeA);
            }
        }


        return result;
    }

//    private static boolean removeFromArray(Object[] array, int index){
//
//    }

    private static boolean checkPlayWay(String lotteryId, String playA, String playB){
        boolean result = true;
        if(lotteryId.equals("51") || lotteryId.equals("7") || lotteryId.equals("4") || lotteryId.equals("73")){
            if((playA.equals("五星") || playA.equals("四星")) && !playB.equals("直选复式")){
                result = false;
            }
        }

        return result;
    }

    /**
     * 根据玩法id获取布局的精确数据
     * @param dataAll
     * @param palyId
     * @return
     */
    public static List<BetListItemModel> getBetListItem(List<BetBigBigModel> dataAll, String palyId){
        List<BetListItemModel> result = new ArrayList<>();

        for(BetBigBigModel betBigBigModel : dataAll){
            for(BetBigModel betBigModel : betBigBigModel.getLabel()){
                for(BetBigItemModel betBigItemModel : betBigModel.getLabel()){
                    if(Integer.parseInt(palyId) == betBigItemModel.getMethodid() && betBigItemModel.getSelectarea() != null
                            && betBigItemModel.getSelectarea().getLayout() != null){
                        result = betBigItemModel.getSelectarea().getLayout();
                    }
                }
            }
        }

        return result;
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
