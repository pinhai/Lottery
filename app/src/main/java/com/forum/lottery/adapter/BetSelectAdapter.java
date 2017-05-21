package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.forum.lottery.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin_h on 2017/5/21.
 */

public class BetSelectAdapter extends BaseAdapter {

    private List<Boolean> selecteds;
    private Context context;
    private OnCheckedListener checkedListener;

    public BetSelectAdapter(Context context, List<Boolean> selecteds, OnCheckedListener checkedListener){
        this.context = context;
        this.selecteds = selecteds;
        this.checkedListener = checkedListener;
    }

    @Override
    public int getCount() {
        return selecteds.size();
    }

    @Override
    public Object getItem(int position) {
        return selecteds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bet_selector, null);
            viewHolder.ctv_bet = (CheckBox) convertView.findViewById(R.id.ctv_bet);
            viewHolder.tv_bet = (CheckedTextView) convertView.findViewById(R.id.tv_bet);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_bet.setText("" + position);
        viewHolder.ctv_bet.setChecked(selecteds.get(position));
        viewHolder.ctv_bet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selecteds.set(position, isChecked);
                checkedListener.onCheckedChanged(isChecked);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        CheckedTextView tv_bet;
        CheckBox ctv_bet;
    }

    public interface OnCheckedListener extends Serializable {
        void onCheckedChanged(boolean isChecked);
    }
}
