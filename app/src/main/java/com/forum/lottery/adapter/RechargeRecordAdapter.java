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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recharge_record, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_rechargeWay = (TextView) convertView.findViewById(R.id.tv_rechargeWay);
            viewHolder.tv_payNo = (TextView) convertView.findViewById(R.id.tv_payNo);
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
        viewHolder.tv_rechargeWay.setText(item.getTradeName());
        viewHolder.tv_payNo.setText(item.getPaymentNo());
        viewHolder.tv_price.setText(item.getTradeMoney() + "元");
        viewHolder.tv_date.setText(item.getCreateDate());
    }

    class ViewHolder{
        TextView tv_rechargeWay, tv_payNo, tv_price, tv_date, tv_status;
    }
}
