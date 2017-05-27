package com.forum.lottery.api;

import com.forum.lottery.entity.BetResult;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.RegisterResult;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.model.BetDetailModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Single;

/**
 * Created by admin_h on 2017/5/22.
 */

public interface LotteryService {

    /**
     * 获取彩票列表
     * @return
     */
    @GET("nonAuthority/gameCenter/appLotteryBuy")
    Single<ResultData<List<LotteryVO>>> getAllLotteryList();

    /**
     * 获取彩票
     * @return
     */
    @GET("nonAuthority/gameCenter/appLotteryBuy")
    Single<LotteryVO> getLottery(@Field("id") String id);

    /**
     * 下注
     * @param betDetails
     * @return
     */
    @POST("nonAuthority/buycp")
    @FormUrlEncoded
    Single<BetResult> bet(@Field("") List<BetDetailModel> betDetails);

    /**
     * 校验提交的购买号是否合理：1、码是否能重复   2、码是否缺失  3、号码范围
     * @return
     */
    @GET("nonAuthority/home/lotteryNumsCheck")
    Single<ResultData> lotteryNumsCheck(@Query("buyNo") String buyNO, @Query("cpCategoryId") String cpCategoryId, @Query("playTypeId") String playTypeId);
//    Single<ResultData> lotteryNumsCheck(@Path(value = "url", encoded = true) String url);
}
