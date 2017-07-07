package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.utils.LotteryIconUtils;
import com.forum.lottery.utils.LotteryUtils;
import com.forum.lottery.view.roundimage.RoundedImageView;

import java.util.List;

/**
 * Created by admin on 2017/5/28.
 */

public class LotteryGridAdapter extends BaseAdapter {

    private Context context;
    private List<LotteryVO> data;

    public LotteryGridAdapter(Context context, List<LotteryVO> lotteryVOs){
        this.context = context;
        data = lotteryVOs;

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_lottery, null);
            viewHolder = new ViewHolder();
            viewHolder.riv_lotteryPic = (RoundedImageView) convertView.findViewById(R.id.riv_lotteryPic);
            viewHolder.tv_lotteryName = (TextView) convertView.findViewById(R.id.tv_lotteryName);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position, viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        if(data.size() < 10){
            return;
        }
        if(position == 9){
            viewHolder.riv_lotteryPic.setImageResource(R.mipmap.logo_more_yellow);
            viewHolder.tv_lotteryName.setText("更多彩种");
            viewHolder.tv_time.setVisibility(View.GONE);
        }else {
            LotteryVO item = data.get(position);
            viewHolder.riv_lotteryPic.setImageResource(LotteryIconUtils.getLotteryIcon(Integer.parseInt(item.getLotteryid())));
            viewHolder.tv_lotteryName.setText(item.getLotteryName());
            viewHolder.tv_time.setVisibility(View.VISIBLE);
            String time = LotteryUtils.secToTime(item.getTime());
            if(time.equals("00:00:00")){
                viewHolder.tv_time.setText("正在开奖");
            }else {
                viewHolder.tv_time.setText(time);
            }
        }

    }

    public void setData(List<LotteryVO> lotteryVOs) {
        this.data = lotteryVOs;
    }

    class ViewHolder{
        RoundedImageView riv_lotteryPic;
        TextView tv_lotteryName, tv_time ;
    }
}
