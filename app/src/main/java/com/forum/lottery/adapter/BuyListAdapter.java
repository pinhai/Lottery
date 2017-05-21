package com.forum.lottery.adapter;

import android.content.Context;

import com.forum.lottery.R;
import com.forum.lottery.entity.LotteryVO;

import java.util.List;

/**
 * Created by Administrator on 2017/5/1.
 */

public class BuyListAdapter extends SingleQuickAdapter<LotteryVO> {
    public BuyListAdapter(Context context, List<LotteryVO> datas) {
        super(context, R.layout.list_buy_item_view, datas);
    }

    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, LotteryVO data, int position) {

    }
}
