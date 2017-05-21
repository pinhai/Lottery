package com.forum.lottery.adapter.lottery;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ViewHoldHelper;
import com.forum.lottery.entity.LotteryVO;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Lottery4Converter extends LotteryBaseConverter {
    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, LotteryVO item, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.list_lottery_item_4_view;
    }
}
