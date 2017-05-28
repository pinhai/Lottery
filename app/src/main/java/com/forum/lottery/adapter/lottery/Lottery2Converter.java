package com.forum.lottery.adapter.lottery;

import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ViewHoldHelper;
import com.forum.lottery.entity.LotteryVO;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Lottery2Converter extends LotteryBaseConverter {
    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, LotteryVO item, int position) {
        TextView[] tv_num = {holdHelper.findView(R.id.tv_num1),
                holdHelper.findView(R.id.tv_num2),
                holdHelper.findView(R.id.tv_num3),
                holdHelper.findView(R.id.tv_num4),
                holdHelper.findView(R.id.tv_num5),
                holdHelper.findView(R.id.tv_num6),
                holdHelper.findView(R.id.tv_num7)
        };
        String[] openNum = item.getOpenNum();
        if(tv_num.length != openNum.length){
            return;
        }
        for(int i=0; i<openNum.length; i++){
            tv_num[i].setText(openNum[i]);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_lottery_item_2_view;
    }
}
