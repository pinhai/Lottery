package com.forum.lottery.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.TrendModel;

import java.util.List;

/**
 * Created by admin_h on 2017/5/28.
 */

public class TrendAdapter extends BaseAdapter {

    private Context context;
    private List<TrendModel> trendModels;

    private int numColumns = 11;
    private Weishu weishu;

    public enum Weishu{
        WAN, QIAN, BAI, SHI, GE
    }

    public TrendAdapter(Context context, List<TrendModel> trendModels, Weishu weishu){
        this.context = context;
        this.trendModels = trendModels;
        this.weishu = weishu;

    }

    @Override
    public int getCount() {
        return getCount(trendModels);
    }

    private int getCount(List<TrendModel> list){
        if(list.size() == 0){
            return 0;
        }
        int count = 0;
        int itemLenth = list.get(0).getAllcode().length;
        count = (list.size()+1)*numColumns;

        return count;
    }

    @Override
    public Object getItem(int position) {
        return trendModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_trend, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_trendNum = (TextView) convertView.findViewById(R.id.tv_trendNum);
            viewHolder.ll_trend = (RelativeLayout) convertView.findViewById(R.id.ll_trend);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setView(position, viewHolder);

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setView(int position, ViewHolder viewHolder) {
        viewHolder.tv_trendNum.setBackground(null);
        if(position == 0){
            viewHolder.tv_trendNum.setText("期数");
        }else if(position < numColumns){
            viewHolder.tv_trendNum.setText("" + (position-1));
        }else{
            if(position%numColumns == 0){
                //设置期数
                int row = position/numColumns - 1;
                viewHolder.tv_trendNum.setText(trendModels.get(row).getIssue());
            }else{
                //设置grid数据
                int row = position/numColumns - 1;
                int col = position%numColumns - 1;
                String[] items = trendModels.get(row).getAllcode();
                int weishu = getWeishu(items);
                String showItem = items[weishu];
                if(col == Integer.parseInt(showItem)){
                    viewHolder.tv_trendNum.setText(items[weishu]);
                    viewHolder.tv_trendNum.setBackgroundResource(R.drawable.bg_red_circle);
                }else{
                    viewHolder.tv_trendNum.setText("");
                }

            }
        }
        viewHolder.ll_trend.setBackgroundColor(getGridColor(position));
    }

    class ViewHolder{
        TextView tv_trendNum;
        RelativeLayout ll_trend;
    }

    public void setWeishu(Weishu weishu){
        this.weishu = weishu;
    }

    private int getWeishu(String[] items) {
        int result = 0;
        switch (weishu){
            case GE:
                result = items.length - 1;
                break;
            case SHI:
                result = items.length - 2;
                break;
            case BAI:
                result = items.length -3;
                break;
            case QIAN:
                result = items.length -4;
                break;
            case WAN:
                result = items.length - 5;

        }
        return result;
    }

    private int getGridColor(int position){
        int color;
        if(position/numColumns == 0){
            color = context.getResources().getColor(R.color.trend_grid_bg1);
        }else if((position/numColumns)%2 == 1){
            color = context.getResources().getColor(R.color.white);
        }else{
            color = context.getResources().getColor(R.color.trend_grid_bg2);
        }

        return color;
    }
}
