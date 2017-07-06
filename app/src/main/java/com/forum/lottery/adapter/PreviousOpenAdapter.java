package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.TrendModel;
import com.forum.lottery.utils.LotteryUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class PreviousOpenAdapter extends BaseAdapter{

    private Context context;
    private List<TrendModel> data;

    public PreviousOpenAdapter(Context context, List<TrendModel> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_previous_open_lottery, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_issue = (TextView) convertView.findViewById(R.id.tv_issue);
            viewHolder.tv_openNum = (TextView) convertView.findViewById(R.id.tv_openNum);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TrendModel item = data.get(position);
        viewHolder.tv_issue.setText(item.getIssue() + "期");
        viewHolder.tv_openNum.setText(LotteryUtils.getOpenNumStr(item.getAllcode()));

        return convertView;
    }

    class ViewHolder{
        TextView tv_issue, tv_openNum;
    }
}
