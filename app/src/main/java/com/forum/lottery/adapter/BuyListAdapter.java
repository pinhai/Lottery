package com.forum.lottery.adapter;

import android.content.Context;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.utils.LogUtils;
import com.forum.lottery.utils.LotteryIconUtils;
import com.forum.lottery.utils.LotteryUtils;
import com.forum.lottery.view.roundimage.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/1.
 */

public class BuyListAdapter extends SingleQuickAdapter<LotteryVO> {
    public BuyListAdapter(Context context, List<LotteryVO> datas) {
        super(context, R.layout.list_buy_item_view, datas);
    }

    @Override
    protected int getItemViewTypeM(int position) {
        return 0;
    }

    @Override
    protected void setViewHolder(ViewHoldHelper holdHelper, final LotteryVO data, int position) {
        RoundedImageView riv_lotteryPic = holdHelper.findView(R.id.riv_lotteryPic);
        TextView tv_lotteryName = holdHelper.findView(R.id.tv_lotteryName);
        TextView tv_issue = holdHelper.findView(R.id.tv_issue);
        TextView tv_openNum = holdHelper.findView(R.id.tv_openNum);
        TextView tv_nextIssue = holdHelper.findView(R.id.tv_nextIssue);
        TextView tv_time = holdHelper.findView(R.id.tv_time);

        riv_lotteryPic.setImageResource(LotteryIconUtils.getLotteryIcon(Integer.parseInt(data.getLotteryid())));
        tv_lotteryName.setText(data.getLotteryName());
        tv_issue.setText("第" + data.getIssue() + "期");
        String openNum = "";
        for(String s : data.getOpenNum()){
            openNum = openNum + " " + s;
        }
        tv_openNum.setText(openNum);
        tv_nextIssue.setText("距离" + data.getNextIssue() + "期 截止还有");
        tv_time.setText(LotteryUtils.secToTime(data.getTime()));


    }


}
