package com.forum.lottery.adapter.lottery;

import android.text.TextUtils;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ViewHoldHelper;
import com.forum.lottery.entity.LotteryVO;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Lottery5Converter extends LotteryBaseConverter {
    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, LotteryVO item, int position) {
        TextView tv_num = holdHelper.findView(R.id.tv_num);
        String showNum = "";
        String[] openNum = item.getOpenNum();
        for(int i=0; i<openNum.length; i++){
            if(TextUtils.isEmpty(openNum[i])){
                openNum[i] = "0";
            }
            if(i == openNum.length-1){
                showNum += openNum[i];
            }else if(i == openNum.length-2){
                showNum += openNum[i] + "=";
            }else{
                showNum += openNum[i] + "+" ;
            }
        }
        tv_num.setText(showNum);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_lottery_item_5_view;
    }
}
