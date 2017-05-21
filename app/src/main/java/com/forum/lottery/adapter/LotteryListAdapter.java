package com.forum.lottery.adapter;

import android.content.Context;
import android.util.SparseArray;

import com.forum.lottery.adapter.lottery.Lottery0Converter;
import com.forum.lottery.adapter.lottery.Lottery1Converter;
import com.forum.lottery.adapter.lottery.Lottery2Converter;
import com.forum.lottery.adapter.lottery.Lottery3Converter;
import com.forum.lottery.adapter.lottery.Lottery4Converter;
import com.forum.lottery.adapter.lottery.Lottery5Converter;
import com.forum.lottery.adapter.lottery.Lottery6Converter;
import com.forum.lottery.entity.LotteryVO;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class LotteryListAdapter extends QuickAdapter<LotteryVO> {
    private int viewType = -1;
    private boolean isHideTitle;
    private SparseArray<ViewConverter> cacheViewConverters;
    public LotteryListAdapter(Context context, List<LotteryVO> datas) {
        super(context, datas);
        cacheViewConverters = new SparseArray<>();
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setHideTitle(boolean hideTitle) {
        isHideTitle = hideTitle;
    }

    public boolean isHideTitle() {
        return isHideTitle;
    }

    @Override
    protected ViewConverter getViewConverter(int viewType) {
        ViewConverter viewConverter = cacheViewConverters.get(viewType);
        if(viewConverter == null){
            switch (viewType){
                case 0:
                    viewConverter = new Lottery0Converter();
                    break;
                case 1:
                    viewConverter = new Lottery1Converter();
                    break;
                case 2:
                    viewConverter = new Lottery2Converter();
                    break;
                case 3:
                    viewConverter = new Lottery3Converter();
                    break;
                case 4:
                    viewConverter = new Lottery4Converter();
                    break;
                case 5:
                    viewConverter = new Lottery5Converter();
                    break;
                case 6:
                    viewConverter = new Lottery6Converter();
                    break;
                default:
                    viewConverter = new Lottery6Converter();
                    break;
            }
        }
        return viewConverter;
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if(viewType != -1)
            return viewType;
        return position%7;
    }
}
