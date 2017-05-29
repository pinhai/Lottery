package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.WinnerModel;

import java.util.List;

/**
 * Created by admin_h on 2017/5/28.
 */

public class WinningListAdapter extends BaseAdapter{

    private Context context;
    private List<WinnerModel> data;

    public WinningListAdapter(Context context, List<WinnerModel> data ){
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return  Integer.MAX_VALUE; //基本可以认为有无穷多项，从而实现循环。
    }

    @Override
    public Object getItem(int position) {
        return data.get(position%data.size());
    }

    @Override
    public long getItemId(int position) {
        return position%data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_winning, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tv_winMoney = (TextView) convertView.findViewById(R.id.tv_winMoney);
            viewHolder.tv_lottery = (TextView) convertView.findViewById(R.id.tv_lottery);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position%data.size(), viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        WinnerModel item = data.get(position);
        viewHolder.tv_username.setText(item.getUser());
        viewHolder.tv_winMoney.setText("喜中" + item.getWinAmount() + "元");
        if(item.getGameName() != null){
            viewHolder.tv_lottery.setText("购买" + item.getGameName());
        }
    }

    class ViewHolder{
        TextView tv_username, tv_winMoney, tv_lottery;
    }
}
