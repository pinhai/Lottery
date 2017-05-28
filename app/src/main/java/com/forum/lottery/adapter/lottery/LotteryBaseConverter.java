package com.forum.lottery.adapter.lottery;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.LotteryListAdapter;
import com.forum.lottery.adapter.ViewConverter;
import com.forum.lottery.adapter.ViewHoldHelper;
import com.forum.lottery.entity.LotteryVO;

/**
 * Created by Administrator on 2017/4/30.
 */

public abstract class LotteryBaseConverter implements ViewConverter {

    @Override
    public void onConvert(BaseAdapter adapter, ViewHoldHelper viewHoldHelper, int position) {
        LotteryListAdapter lotteryListAdapter = (LotteryListAdapter) adapter;
        LotteryVO lotteryVO = lotteryListAdapter.get(position);
        TextView txtTitle = viewHoldHelper.findView(R.id.txt_title);
        TextView tv_periods_date = viewHoldHelper.findView(R.id.tv_periods_date);
        if(lotteryListAdapter.isHideTitle()){
            txtTitle.setVisibility(View.GONE);
        }
        txtTitle.setText(lotteryVO.getLotteryName());
        tv_periods_date.setText("第" + lotteryVO.getIssue() + "期  " + lotteryVO.getOpenTime());

        setViewHolder(viewHoldHelper, lotteryListAdapter.get(position), position);
    }

    protected abstract void setViewHolder(ViewHoldHelper holdHelper, LotteryVO item,
                                          int position);
}
