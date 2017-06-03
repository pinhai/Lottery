package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.forum.lottery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin_h on 2017/5/29.
 */

public class TrendWeishuAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    private List<Boolean> itemChecked;
    private OnItemCheckedListener itemCheckedListener;

    public TrendWeishuAdapter(Context context, List<String> weishuStrings, OnItemCheckedListener listener){
        this.context = context;
        this.data = weishuStrings;

        this.itemCheckedListener = listener;

        itemChecked = new ArrayList<>();
        initItemCheck();
    }

    public void setData(List<String> data){
        this.data = data;
        initItemCheck();
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_trend_weishu, null);
            viewHolder = new ViewHolder();
            viewHolder.rb_weishu = (RadioButton) convertView.findViewById(R.id.rb_weishu);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position, viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        viewHolder.rb_weishu.setText(data.get(position));
        if(itemChecked.get(position)){
//            viewHolder.rb_weishu.setTextColor(context.getResources().getColor(R.color.red));
            viewHolder.rb_weishu.setChecked(true);
        }else{
//            viewHolder.rb_weishu.setTextColor(context.getResources().getColor(R.color.gray_dark));
            viewHolder.rb_weishu.setChecked(false);
        }
        viewHolder.rb_weishu.setOnClickListener(new ClickListener(position));
    }

    class ViewHolder{
        RadioButton rb_weishu;
    }

    private class ClickListener implements View.OnClickListener{

        private int position;

        public ClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            for(int i=0; i<itemChecked.size(); i++){
                if(position == i){
                    itemChecked.set(i, true);
                }else{
                    itemChecked.set(i, false);
                }
            }
            notifyDataSetChanged();
            itemCheckedListener.onChecked(data.get(position), position, data.size());
        }
    }

    public void initItemCheck() {
        itemChecked.clear();
        for(int i=0; i<data.size(); i++){
            if(i == 0){
                itemChecked.add(true);
                itemCheckedListener.onChecked(data.get(i), i, data.size());
            }else{
                itemChecked.add(false);
            }
        }
    }

    public interface OnItemCheckedListener {
        void onChecked(String value, int position, int weishuCount);
    }
}
