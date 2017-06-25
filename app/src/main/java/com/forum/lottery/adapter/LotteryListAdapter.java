package com.forum.lottery.adapter;

import android.content.Context;
import android.util.SparseArray;

import com.forum.lottery.adapter.lottery.Lottery0Converter;
import com.forum.lottery.adapter.lottery.Lottery1Converter;
import com.forum.lottery.adapter.lottery.Lottery3Converter;
import com.forum.lottery.adapter.lottery.Lottery4Converter;
import com.forum.lottery.adapter.lottery.Lottery5Converter;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.model.LotteryType;

import java.util.List;

import static com.forum.lottery.model.LotteryType.AHKS;
import static com.forum.lottery.model.LotteryType.BJ28;
import static com.forum.lottery.model.LotteryType.BJPKS;
import static com.forum.lottery.model.LotteryType.CQSSC;
import static com.forum.lottery.model.LotteryType.FC3D;
import static com.forum.lottery.model.LotteryType.GD11X5;
import static com.forum.lottery.model.LotteryType.JX11X5;
import static com.forum.lottery.model.LotteryType.PLS;
import static com.forum.lottery.model.LotteryType.SD11X5;
import static com.forum.lottery.model.LotteryType.SFPKS;
import static com.forum.lottery.model.LotteryType.SFSSC;
import static com.forum.lottery.model.LotteryType.SH11X5;
import static com.forum.lottery.model.LotteryType.SHSSL;
import static com.forum.lottery.model.LotteryType.TJSSC;
import static com.forum.lottery.model.LotteryType.XY28;
import static com.forum.lottery.model.LotteryType.XJSSC;

/**
 * Created by Administrator on 2017/4/30.
 */

public class LotteryListAdapter extends QuickAdapter<LotteryVO> {
    private int viewType = -1;
    private boolean isHideTitle;
    private SparseArray<ViewConverter> cacheViewConverters;
    private List<LotteryVO> datas;
    public LotteryListAdapter(Context context, List<LotteryVO> datas) {
        super(context, datas);
        this.datas = datas;
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
            if(viewType == SFSSC.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == CQSSC.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == BJPKS.value()){
                viewConverter = new Lottery3Converter();
            }else if(viewType == XY28.value()){
                viewConverter = new Lottery5Converter();
            }else if(viewType == SFPKS.value()){
                viewConverter = new Lottery3Converter();
            }else if(viewType == SHSSL.value()){
                viewConverter = new Lottery0Converter();
            }else if(viewType == TJSSC.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == XJSSC.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == SD11X5.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == SH11X5.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == JX11X5.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == GD11X5.value()){
                viewConverter = new Lottery1Converter();
            }else if(viewType == BJ28.value()){
                viewConverter = new Lottery5Converter();
            }else if(viewType == AHKS.value()){
                viewConverter = new Lottery4Converter();
            }else if(viewType == FC3D.value()){
                viewConverter = new Lottery0Converter();
            }else if(viewType == PLS.value()){
                viewConverter = new Lottery0Converter();
            }else {
                viewConverter = new Lottery0Converter();
            }

            cacheViewConverters.put(viewType, viewConverter);

        }
        return viewConverter;
    }

    @Override
    public int getViewTypeCount() {
        return LotteryType.values().length;
    }

    @Override
    public int getItemViewTypeM(int position) {

        int lotteryId = Integer.parseInt(datas.get(position).getLotteryid());
        return  lotteryId;

//        if(viewType != -1)
//            return viewType;
//        return position%7;
    }
}
