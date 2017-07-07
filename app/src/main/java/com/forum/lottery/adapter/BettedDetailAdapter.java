package com.forum.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.BetDetailModel;

import java.util.List;

/**
 * Created by admin on 2017/5/25.
 */

public class BettedDetailAdapter extends BaseAdapter{

    private Context context;
    private List<BetDetailModel> data;
    private OnDeleteItemListener listener;

    public BettedDetailAdapter(Context context, List<BetDetailModel> data, OnDeleteItemListener listener){
        this.context = context;
        this.data = data;
        this.listener = listener;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_betted_detail, null);
            viewHolder.btn_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);
            viewHolder.tv_buyNo = (TextView) convertView.findViewById(R.id.tv_buyNo);
            viewHolder.tv_peilv_fanli = (TextView) convertView.findViewById(R.id.tv_peilv_fanli);
            viewHolder.tv_type_betcount = (TextView) convertView.findViewById(R.id.tv_type_betcount);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BetDetailModel detail = data.get(position);
        viewHolder.tv_buyNo.setText(detail.getBuyNO());
        viewHolder.tv_peilv_fanli.setText("赔率：" + detail.getPeilv() + "  返利：" + detail.getFanli());
        viewHolder.tv_type_betcount.setText(detail.getPlayTypeName() + "  " + detail.getBuyCount() + "注"
                + "  " + detail.getBuyCount()*detail.getUnitPrice() + "元");
        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                data.remove(position);
                notifyDataSetChanged();
                listener.itemDelete();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageButton btn_delete;
        TextView tv_buyNo, tv_peilv_fanli, tv_type_betcount;
    }

    public interface OnDeleteItemListener{
        void itemDelete();
    }
}
