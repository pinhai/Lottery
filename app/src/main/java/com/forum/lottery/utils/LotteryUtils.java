package com.forum.lottery.utils;

import com.forum.lottery.model.BetItemModel;
import com.forum.lottery.model.BetListItemModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class LotteryUtils {

    public static int getBetCount(List<BetListItemModel> data){
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

}
