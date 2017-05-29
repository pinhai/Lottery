package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.TradeStreamVo;

import java.util.List;

/**
 * Created by admin_h on 2017/5/29.
 */

public class RechargeRecordAdapter extends BaseAdapter{

    private Context context;
    private List<TradeStreamVo> data;

    public RechargeRecordAdapter(Context context, List<TradeStreamVo> data){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bet_record, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_cpname = (TextView) convertView.findViewById(R.id.tv_cpname);
            viewHolder.tv_period = (TextView) convertView.findViewById(R.id.tv_period);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position, viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        TradeStreamVo item = data.get(position);
        viewHolder.tv_cpname.setText(item.getTradeName());
//        viewHolder.tv_period.setText(item.getPeriodNO()+ "期");
//        viewHolder.tv_price.setText(item.getCountPrice() + "元");
        viewHolder.tv_date.setText(item.getBeginTime());
    }

    class ViewHolder{
        TextView tv_cpname, tv_period, tv_price, tv_date;
    }
}
