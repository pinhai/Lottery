package com.forum.lottery.adapter.lottery;

import android.widget.ImageView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ViewHoldHelper;
import com.forum.lottery.entity.LotteryVO;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Lottery4Converter extends LotteryBaseConverter {
    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, LotteryVO item, int position) {
        ImageView[] iv_num = {
                holdHelper.findView(R.id.iv_num1),
                holdHelper.findView(R.id.iv_num2),
                holdHelper.findView(R.id.iv_num3)
            };
        String[] openNum = item.getOpenNum();
        if(iv_num.length != openNum.length){
            return;
        }
        for(int i=0; i<openNum.length; i++){
            iv_num[i].setImageResource(getImageResource(Integer.parseInt(openNum[i])));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_lottery_item_4_view;
    }

    private int getImageResource(int num){
        int result;
        switch (num){
            case 1:
                result = R.mipmap.touzi_01;
                break;
            case 2:
                result = R.mipmap.touzi_02;
                break;
            case 3:
                result = R.mipmap.touzi_03;
                break;
            case 4:
                result = R.mipmap.touzi_04;
                break;
            case 5:
                result = R.mipmap.touzi_05;
                break;
            case 6:
                result = R.mipmap.touzi_06;
                break;
            default:
                result = R.mipmap.touzi_06;
        }
        return result;
    }
}
