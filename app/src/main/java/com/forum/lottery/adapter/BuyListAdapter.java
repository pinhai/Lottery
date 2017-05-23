package com.forum.lottery.adapter;

import android.content.Context;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.entity.LotteryVO;
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
    protected void setViewHolder(ViewHoldHelper holdHelper, final LotteryVO data, int position) {
        RoundedImageView riv_lotteryPic = holdHelper.findView(R.id.riv_lotteryPic);
        TextView tv_lotteryName = holdHelper.findView(R.id.tv_lotteryName);
        TextView tv_issue = holdHelper.findView(R.id.tv_issue);
        TextView tv_openNum = holdHelper.findView(R.id.tv_openNum);
        TextView tv_nextIssue = holdHelper.findView(R.id.tv_nextIssue);
        TextView tv_time = holdHelper.findView(R.id.tv_time);

        tv_lotteryName.setText(data.getLotteryName());
        tv_issue.setText("第" + data.getIssue() + "期");
        String openNum = "";
        for(String s : data.getOpenNum()){
            openNum = openNum + " " + s;
        }
        tv_openNum.setText(openNum);
        tv_nextIssue.setText("距离" + data.getNextIssue() + "期 截止还有");
        tv_time.setText(secToTime(data.getTime()));


    }

    // a integer to xx:xx:xx
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

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
