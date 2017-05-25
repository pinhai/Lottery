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
 * Created by admin_h on 2017/5/22.
 */

public class ActionMenuAdapter extends BaseAdapter{

    private Context context;
    private List<String> menu;
    private List<Boolean> itemChecked;
    private OnItemCheckedListener itemCheckedListener;

    public ActionMenuAdapter(Context context, List<String> menu, OnItemCheckedListener listener){
        this.context = context;
        this.menu = menu;
        this.itemCheckedListener = listener;

        itemChecked = new ArrayList<>();
        initItemCheck();
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_action_menu, null);
            viewHolder.rb_action_item = (RadioButton) convertView.findViewById(R.id.rb_action_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rb_action_item.setText(menu.get(position));
        if(itemChecked.get(position)){
            viewHolder.rb_action_item.setTextColor(context.getResources().getColor(R.color.red));
            viewHolder.rb_action_item.setChecked(true);
        }else{
            viewHolder.rb_action_item.setTextColor(context.getResources().getColor(R.color.gray_dark));
            viewHolder.rb_action_item.setChecked(false);
        }
        viewHolder.rb_action_item.setOnClickListener(new ClickListener(position));
//        viewHolder.rb_action_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//
//                }
//            }
//        });

        return convertView;
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
            itemCheckedListener.onChecked(menu.get(position), position);
        }
    }

    private class ViewHolder {
        RadioButton rb_action_item;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void initItemCheck() {
        itemChecked.clear();
        for(int i=0; i<menu.size(); i++){
            if(i == 0){
                itemChecked.add(true);
                itemCheckedListener.onChecked(menu.get(i), i);
            }else{
                itemChecked.add(false);
            }
        }
    }

    public interface OnItemCheckedListener {
        void onChecked(String value, int position);
    }
}
