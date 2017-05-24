package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.BetItemModel;
import com.forum.lottery.model.BetListItemModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 彩票下注界面-选择数字
 * Created by admin_h on 2017/5/23.
 */

public class SelectLotteryListAdapter extends BaseAdapter {

    private Context context;
    private List<BetListItemModel> data;
    private List<BetSelectAdapter> adapters;
//    private List<List<Boolean>> selectedsALl;

    public SelectLotteryListAdapter(Context context, List<BetListItemModel> data){
        this.context = context;
        this.data = data;
//        selectedsALl = new ArrayList<>();
        adapters = new ArrayList<>();
        for(int i=0; i<data.size(); i++){
            List<Boolean> selecteds = new ArrayList<>();
//            Map<String, List<BetItemModel>> item = data.get(i);
//            String label = "";
//            Set set = item.keySet();
//            Iterator iter = set.iterator();
//            if (iter.hasNext()) {
//                label = (String) iter.next();
//            }
            List<BetItemModel> itemData = data.get(i).getBetItems();
            BetSelectAdapter adapter = new BetSelectAdapter(context, itemData, new BetSelectAdapter.OnCheckedListener() {
                @Override
                public void onCheckedChanged(boolean isChecked) {

                }
            });
            adapters.add(adapter);

        }
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
//        Map<String, List<BetItemModel>> item = data.get(position);
//        String label = "";
//        Set set = item.keySet();
//        Iterator iter = set.iterator();
//        if (iter.hasNext()) {
//            label = (String) iter.next();
//        }
        viewHolder.tv_label.setText(data.get(position).getLabel());

//        List<BetItemModel> itemData = item.get(label);
        BetSelectAdapter adapter = adapters.get(position);
//        adapter.setItemData(itemData);
        viewHolder.gv_wei.setAdapter(adapter);
    }

    class ViewHolder{
        TextView tv_label;
        GridView gv_wei;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for(int i=0; i<data.size(); i++){
            adapters.get(i).notifyDataSetChanged();
        }
    }

    public void clearCheckedBetting() {
        for(int i=0; i<data.size(); i++){
//            List<Boolean> item = selectedsALl.get(i);
            List<BetItemModel> item = data.get(i).getBetItems();
            for(int j=0; j<item.size(); j++){
                item.get(j).setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

//    public String getMapKey(Map<String, List<BetItemModel>> item){
//        String label = "";
//        Set set = item.keySet();
//        Iterator iter = set.iterator();
//        if (iter.hasNext()) {
//            label = (String) iter.next();
//        }
//
//        return label;
//    }
}
