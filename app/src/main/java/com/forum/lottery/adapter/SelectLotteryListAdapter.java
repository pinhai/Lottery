package com.forum.lottery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.bet.BetItemModel;
import com.forum.lottery.model.bet.BetListItemModel;
import com.forum.lottery.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩票下注界面-选择数字
 * Created by admin_h on 2017/5/23.
 */

public class SelectLotteryListAdapter extends BaseAdapter {

    private Context context;
    private List<BetListItemModel> data;
    private List<BetSelectAdapter> adapters;

    String lotteryId;

    public SelectLotteryListAdapter(Context context, List<BetListItemModel> data, String lotteryId){
        this.context = context;
        this.data = data;
        this.lotteryId = lotteryId;
        setGridAdapter();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bet_grid, null);
            viewHolder = new ViewHolder();
            viewHolder.ll_betGrid = (LinearLayout) convertView.findViewById(R.id.ll_betGrid);
            viewHolder.tv_label = (TextView) convertView.findViewById(R.id.tv_label);
            viewHolder.gv_wei = (GridView) convertView.findViewById(R.id.gv_wei);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position, viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        if(TextUtils.isEmpty(data.get(position).getTitle())){
            viewHolder.tv_label.setVisibility(View.GONE);
        }else {
            viewHolder.tv_label.setVisibility(View.VISIBLE);
            viewHolder.tv_label.setText(data.get(position).getTitle());
        }

        if(position > 0 && data.size() > 0 && ((data.get(0).getTitle().equals("和值") && lotteryId.equals("11")
            || (lotteryId.equals("41") || lotteryId.equals("42")) && data.size()>0 && !data.get(0).getTitle().equals("特码包三")))
                ){
            viewHolder.ll_betGrid.setVisibility(View.GONE);
            return;
        }else{
            viewHolder.ll_betGrid.setVisibility(View.VISIBLE);
        }
        BetSelectAdapter adapter = adapters.get(position);
        List<BetItemModel> itemData = adapter.getData();
        if(itemData != null && itemData.size() > 0&& StringUtils.isChinese(itemData.get(0).getName())
                && itemData.get(0).getName().length() > 2){
            viewHolder.gv_wei.setNumColumns(2);
        }else{
            viewHolder.gv_wei.setNumColumns(5);
        }
        viewHolder.gv_wei.setAdapter(adapter);
    }

    public void setData(List<BetListItemModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder{
        LinearLayout ll_betGrid;
        TextView tv_label;
        GridView gv_wei;
    }

    private void setGridAdapter(){
        adapters = new ArrayList<>();

        if((lotteryId.equals("11") && data.size() > 0 && data.get(0).getTitle().equals("和值"))
                || ((lotteryId.equals("41") || lotteryId.equals("42")) && data.size()>0 && !data.get(0).getTitle().equals("特码包三"))){
            List<BetItemModel> itemData = new ArrayList<>();
            for (BetListItemModel betListItemModel : data){
//                BetItemModel itemModel = new BetItemModel(betListItemModel.getNo(), betListItemModel.getBetItems().get(0).isChecked());
                itemData.addAll(betListItemModel.getBetItems());
            }
            BetSelectAdapter adapter = new BetSelectAdapter(context, itemData, new BetSelectAdapter.OnCheckedListener() {
                @Override
                public void onCheckedChanged(boolean isChecked) {

                }
            });
            adapters.add(adapter);

        }
        else{
            for(int i=0; i<data.size(); i++){
                List<BetItemModel> itemData = data.get(i).getBetItems();
                BetSelectAdapter adapter = new BetSelectAdapter(context, itemData, new BetSelectAdapter.OnCheckedListener() {
                    @Override
                    public void onCheckedChanged(boolean isChecked) {

                    }
                });
                adapters.add(adapter);

            }
        }

    }

    @Override
    public void notifyDataSetChanged() {
        setGridAdapter();
        super.notifyDataSetChanged();
//        for(int i=0; i<data.size(); i++){
//            adapters.get(i).notifyDataSetChanged();
//        }
    }

    public void clearCheckedBetting() {
        for(int i=0; i<data.size(); i++){
            List<BetItemModel> item = data.get(i).getBetItems();
            for(int j=0; j<item.size(); j++){
                item.get(j).setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

}
