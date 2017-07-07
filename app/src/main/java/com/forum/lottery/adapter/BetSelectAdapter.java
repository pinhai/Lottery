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
import com.forum.lottery.event.BuyLotteryCheckChangeEvent;
import com.forum.lottery.model.bet.BetItemModel;
import com.forum.lottery.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/5/21.
 */

public class BetSelectAdapter extends BaseAdapter {

//    private List<Boolean> selecteds;
    private Context context;
    private OnCheckedListener checkedListener;
    private List<BetItemModel> itemData;
    String lotteryId;

//    public BetSelectAdapter(Context context, List<Boolean> selecteds, OnCheckedListener checkedListener){
//        this.context = context;
//        this.selecteds = selecteds;
//        this.checkedListener = checkedListener;
//    }

    public BetSelectAdapter(Context context, String lotteryId, List<BetItemModel> itemData, OnCheckedListener checkedListener){
        this.context = context;
//        this.selecteds = selecteds;
        this.checkedListener = checkedListener;
        this.itemData = itemData;
        this.lotteryId = lotteryId;
    }

//    public void setItemData(List<String> itemData){
//        this.itemData = itemData;
//    }

    @Override
    public int getCount() {
        return itemData.size();
    }

    @Override
    public Object getItem(int position) {
        return itemData.get(position);
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
            viewHolder.tv_prize = (TextView) convertView.findViewById(R.id.tv_prize);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String betName = itemData.get(position).getName();
        if((betName.length() > 2 && StringUtils.isChinese(betName)) || (lotteryId.equals("18") && StringUtils.isChinese(betName))){
            viewHolder.ctv_bet.setButtonDrawable(R.drawable.bg_bet_chinese_selector);

        }else{
            viewHolder.ctv_bet.setButtonDrawable(R.drawable.bg_bet_selector);
        }
        viewHolder.tv_bet.setText(betName);
        viewHolder.ctv_bet.setChecked(itemData.get(position).isChecked());
        viewHolder.ctv_bet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                selecteds.set(position, isChecked);
                itemData.get(position).setChecked(isChecked);
                checkedListener.onCheckedChanged(isChecked);
                EventBus.getDefault().post(new BuyLotteryCheckChangeEvent());
            }
        });
        if(itemData.get(position).getPrize() != -1){
            viewHolder.tv_prize.setVisibility(View.VISIBLE);
            viewHolder.tv_prize.setText(itemData.get(position).getPrize()+"");
        }else{
            viewHolder.tv_prize.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {
        CheckedTextView tv_bet;
        CheckBox ctv_bet;
        TextView tv_prize;
    }

    public List<BetItemModel> getData(){
        return itemData;
    }

    public interface OnCheckedListener extends Serializable {
        void onCheckedChanged(boolean isChecked);
    }
}
