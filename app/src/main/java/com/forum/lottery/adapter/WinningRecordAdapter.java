package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.BetRecordModel;
import com.forum.lottery.model.BetRecordModel;
import com.forum.lottery.model.PrizeUserVo;

import java.util.List;

/**
 * 中奖纪录
 * Created by admin_h on 2017/5/29.
 */

public class WinningRecordAdapter extends BaseAdapter{

    private Context context;
    private List<PrizeUserVo> data;

    public WinningRecordAdapter(Context context, List<PrizeUserVo> data){
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_winning_record, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_cpname = (TextView) convertView.findViewById(R.id.tv_cpname);
            viewHolder.tv_period = (TextView) convertView.findViewById(R.id.tv_period);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position, viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        PrizeUserVo item = data.get(position);
        viewHolder.tv_cpname.setText(item.getGameName());
//        viewHolder.tv_period.setText(item.get+ "期");
//        viewHolder.tv_price.setText(item.getCountPrice() + "元");
//        viewHolder.tv_date.setText(item.getBuyTime());
        viewHolder.tv_status.setText("赢" + item.getWinAmount());
    }

    class ViewHolder{
        TextView tv_cpname, tv_period, tv_price, tv_date, tv_status;
    }
}
