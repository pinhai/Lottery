package com.forum.lottery.adapter.lottery;

import android.util.Log;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ViewHoldHelper;
import com.forum.lottery.entity.LotteryVO;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Lottery1Converter extends LotteryBaseConverter {
    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, LotteryVO item, int position) {

        TextView[] tv_num = {holdHelper.findView(R.id.tv_num1),
                holdHelper.findView(R.id.tv_num2),
                holdHelper.findView(R.id.tv_num3),
                holdHelper.findView(R.id.tv_num4),
                holdHelper.findView(R.id.tv_num5)
        };
        String[] openNum = item.getOpenNum();
        if(tv_num.length != openNum.length){
            return;
        }
        for(int i=0; i<openNum.length; i++){
            if(openNum[i] != null && tv_num[i] != null){
                tv_num[i].setText(openNum[i]);
            }
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.list_lottery_item_1_view;
    }
}
