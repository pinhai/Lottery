package com.forum.lottery.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.TrendModel;
import com.forum.lottery.utils.ScreenUtils;
import com.forum.lottery.view.MarqueeText;

import java.util.List;

/**
 * Created by admin_h on 2017/5/28.
 */

public class TrendAdapter extends BaseAdapter {

    private String weishu;
    private Context context;
    private List<TrendModel> trendModels;

    private int numColumns = 11;
//    private Weishu weishu;
    private int GRIVIEW_COLUMN_HEIGHT = 0;

//    public enum Weishu{
//        WAN(), QIAN, BAI, SHI, GE
//    }

    public TrendAdapter(Context context, List<TrendModel> trendModels, String weishu){
        this.context = context;
        this.trendModels = trendModels;
        this.weishu = weishu;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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

        initKeyTextView(viewHolder.ll_trend, position);
        setView(position, viewHolder);

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setView(int position, ViewHolder viewHolder) {
        int row = position/numColumns;
        int col = position%numColumns;
        viewHolder.tv_trendNum.setBackground(null);
        if(row == 0){
            if(col == 0){
                viewHolder.tv_trendNum.setText("期数");
            }else{
                viewHolder.tv_trendNum.setText((col-1) + "");
            }
            viewHolder.ll_trend.setBackgroundColor(context.getResources().getColor(R.color.trend_grid_bg1));
        }else{
            if(col == 0){
                String qishu = trendModels.get(row-1).getIssue();
                viewHolder.tv_trendNum.setText(qishu);
            }else{
                TrendModel trendModel = trendModels.get(row-1);
                String showNum = trendModel.getAllcode()[getWeishu(trendModel.getAllcode().length)];
                if((col-1) == Integer.parseInt(showNum)){
                    viewHolder.tv_trendNum.setText(showNum);
                    viewHolder.tv_trendNum.setBackgroundResource(R.drawable.bg_red_circle);
                }else{
                    viewHolder.tv_trendNum.setText("");
                }
            }
            if(row%2 == 1){
                viewHolder.ll_trend.setBackgroundColor(context.getResources().getColor(R.color.white));
            }else{
                viewHolder.ll_trend.setBackgroundColor(context.getResources().getColor(R.color.trend_grid_bg2));
            }
        }

    }

    class ViewHolder{
        TextView tv_trendNum;
        RelativeLayout ll_trend;
    }

    public void setWeishu(String weishu){
        this.weishu = weishu;
    }

    private int getWeishu(int length) {
        int result = 0;
        if(weishu.equals(context.getString(R.string.ge))){
            result = length - 1;
        } else if(weishu.equals(context.getString(R.string.shi))){
            result = length - 2;
        }else if(weishu.equals(context.getString(R.string.bai))){
            result = length - 3;
        }else if(weishu.equals(context.getString(R.string.qian))){
            result = length - 4;
        }else if(weishu.equals(context.getString(R.string.wan))){
            result = length - 5;
        }

        return result;
    }

    private int getGridColor(int row){
        int color;
        if(row == 0){
            color = context.getResources().getColor(R.color.trend_grid_bg1);
        }else if(row%2 == 1){
            color = context.getResources().getColor(R.color.white);
        }else{
            color = context.getResources().getColor(R.color.trend_grid_bg2);
        }

        return color;
    }

    /**
     *
     * @方法名称:initKeyTextView
     * @描述: TODO
     * @创建人：yzk
     * @创建时间：2014年10月15日 上午11:58:22
     * @备注：获取高度每个textview的高度,然后进行比较,把最高的设置为TextView的高度
     * @param ll
     * @param position
     * @返回类型：void
     * @注意点:1,要把获取的item中TextView的高度存放到全局变量中,这样才会其作用.
     *        2.一定要在addOnGlobalLayoutListener监听器中给TextView设置高度,
     *         禁止把高度取出,然后在getView中这只高度,这样是无效的
     * @设计思路:1.先把TextView的高度,获取出来
     *         2.把高度存到全局变量中,然后进行和原来的比较,把大的存到全局变量中
     *         3.然后再把全局变量中的高度设置给TextView
     */
    public void initKeyTextView(final View ll, final int position) {
        ViewTreeObserver vto2 = ll.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                int width = 0;
//                int colWidth = (int) ((ScreenUtils.getScreenWidth()-(numColumns-1)*ScreenUtils.dp2px((float) 0.5))/(numColumns+1));
                ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (position % numColumns == 0) {
                    GRIVIEW_COLUMN_HEIGHT = 0;
//                    width = colWidth*2;
                }else {
//                    width = colWidth;
                }
                if (ll.getHeight() > GRIVIEW_COLUMN_HEIGHT) {
                    GRIVIEW_COLUMN_HEIGHT = ll.getHeight();
                }
                setHeight(ll, ViewGroup.LayoutParams.MATCH_PARENT, GRIVIEW_COLUMN_HEIGHT);
            }
        });
    }

    public void setHeight(View ll, int width, int height) {
        ll.setLayoutParams(new AbsListView.LayoutParams(width, height, Gravity.CENTER));

    }
}
