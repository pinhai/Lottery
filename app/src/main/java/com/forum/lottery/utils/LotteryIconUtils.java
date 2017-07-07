package com.forum.lottery.utils;

import com.forum.lottery.R;

import static com.forum.lottery.model.LotteryType.AHKS;
import static com.forum.lottery.model.LotteryType.BJ28;
import static com.forum.lottery.model.LotteryType.BJPKS;
import static com.forum.lottery.model.LotteryType.CQSSC;
import static com.forum.lottery.model.LotteryType.FC3D;
import static com.forum.lottery.model.LotteryType.GD11X5;
import static com.forum.lottery.model.LotteryType.JX11X5;
import static com.forum.lottery.model.LotteryType.LHC;
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
 * Created by admin on 2017/5/28.
 */

public class LotteryIconUtils {

    public static int getLotteryIcon(int cpId){
        int icon;
        if(cpId == SFSSC.value()){
            icon = R.mipmap.sfssc;
        }else if(cpId == CQSSC.value()){
            icon = R.mipmap.cqssc;
        }else if(cpId == BJPKS.value()){
            icon = R.mipmap.bjpks;
        }else if(cpId == XY28.value()){
            icon = R.mipmap.xy28;
        }else if(cpId == SFPKS.value()){
            icon = R.mipmap.sfpks;
        }else if(cpId == SHSSL.value()){
            icon = R.mipmap.shssl;
        }else if(cpId == TJSSC.value()){
            icon = R.mipmap.tjssc;
        }else if(cpId == XJSSC.value()){
            icon = R.mipmap.xjssc;
        }else if(cpId == SD11X5.value()){
            icon = R.mipmap.syxw;
        }else if(cpId == SH11X5.value()){
            icon = R.mipmap.syxw;
        }else if(cpId == JX11X5.value()){
            icon = R.mipmap.syxw;
        }else if(cpId == GD11X5.value()){
            icon = R.mipmap.syxw;
        }else if(cpId == BJ28.value()){
            icon = R.mipmap.bj28;
        }else if(cpId == AHKS.value()){
            icon = R.mipmap.ahks;
        }else if(cpId == FC3D.value()){
            icon = R.mipmap.fc3d;
        }else if(cpId == PLS.value()){
            icon = R.mipmap.pls;
        }else if(cpId == LHC.value()){
            icon = R.mipmap.lhc;
        }
        else {
            icon = R.mipmap.sfssc;
        }

        return icon;
    }

}
