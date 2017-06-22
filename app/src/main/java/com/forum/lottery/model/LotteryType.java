package com.forum.lottery.model;

/**
 * Created by admin_h on 2017/5/28.
 */

public enum LotteryType {

    SFSSC(51),  //三分时时彩
    CQSSC(73),  //重庆时时彩
    BJPKS(9),  //北京PK拾
    XJP28(42),    //新加坡28
    SFPKS(52),   //三分PK拾
    SHSSL(3),   //上海时时乐
    TJSSC(4),   //天津时时彩
    XJSSC(7),   //新疆时时彩
    SD11X5(12),    //山东11选5
    SH11X5(13),   //上海11选5
    JX11X5(14),    //江西11选5
    GD11X5(15),    //广东11选5
//    LHC(),    //六合彩
    BJ28(41),    //北京28
    AHKS(11),   //安徽快三
    FC3D(1),   //福彩3D
    PLS(2);   //排列三

    private int value = 0;

    private LotteryType(int value){
        this.value = value;
    }

    public static LotteryType valueOf(int cpId){
        switch (cpId) {
            case 51:
                return SFSSC;
            case 73:
                return CQSSC;
            case 9:
                return BJPKS;
            case 42:
                return XJP28;
            case 52:
                return SFPKS;
            case 3:
                return SHSSL;
            case 4:
                return TJSSC;
            case 7:
                return XJSSC;
            case 14:
                return JX11X5;
            case 12:
                return SD11X5;
            case 13:
                return SH11X5;
            case 15:
                return GD11X5;
            case 41:
                return BJ28;
            case 11:
                return AHKS;
            case 1:
                return FC3D;
            case 2:
                return PLS;
            default:
                return SFSSC;
        }
    }

    public int value() {
        return this.value;
    }

}
