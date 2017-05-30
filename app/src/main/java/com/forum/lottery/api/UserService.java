package com.forum.lottery.api;

import com.forum.lottery.entity.PageResult;
import com.forum.lottery.model.BankVo;
import com.forum.lottery.model.RefreshMoneyModel;
import com.forum.lottery.entity.RegisterResult;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.model.TradeStreamVo;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Single;

/**
 * Created by Administrator on 2017/5/17.
 */

public interface UserService {
    /**
     * 注册
     * @return
     */
    @POST("nonAuthority/register/regist")
    @FormUrlEncoded
    Single<RegisterResult> register(@Field("username") String userName, @Field("password") String password, @Field("authnum") String code);

    /**
     * 登录
     * @return
     */
    @POST("nonAuthority/userLogin?isapp=1")
    @FormUrlEncoded
    Single<RegisterResult> login(@Field("username") String userName, @Field("password") String password);

    /**
     * 刷新余额
     * @return
     */
    @GET("nonAuthority/checkLogin?")
    Single<RefreshMoneyModel> refreshMoney(@Query("username") String username);

    /**
     * 投注记录
     * @param username
     * @param page
     * @param rows
     * @return
     */
    @GET("/nonAuthority/queryBuycpListForCust")
    Single<ResultData> getBetRecord(@Query("username") String username, @Query("page") int page, @Query("rows") int rows);

    /**
     * 充值记录
     *
     * username:bill
     payIn:1        收支情况：0、支出  1、收入
     tradeType:0    交易类型：0、充值   1、提款  2、投注  3、奖金派送
     page:1         第几页
     rows:10        查询数
     statusCode:0      交易状态：0、未支付 1、待确认 2、已付款
     * @return
     */
    @POST("userCenter/queryRechargeForCust")
    @FormUrlEncoded
    Single<PageResult<TradeStreamVo>> getRechargeRecord(@Field("username") String username, @Field("page") int page, @Field("rows") int rows
                                        , @Field("tradeType") int tradeType, @Field("statusCode") String statusCode);
    /**
     * 中奖记录
     *
     * beginTime:2017-4-1
     endTime:2017-5-31
     username:bill
     tradeType:3
     page:1
     rows:10
     * @return
     */
    @POST("userCenter/queryRechargeForCust")
    @FormUrlEncoded
    Single<ResultData> getWinningRecord(@Field("username") String username, @Field("page") int page, @Field("rows") int rows
                                        , @Field("tradeType") int tradeType);
    /**
     * 提款记录
     *
     * username:bill
     payIn:0
     tradeType:1
     page:1
     rows:10
     * @return
     */
    @POST("userCenter/queryRechargeForCust")
    @FormUrlEncoded
    Single<PageResult<TradeStreamVo>> getDrawMoneyRecord(@Field("username") String username, @Field("page") int page, @Field("rows") int rows
                                        , @Field("tradeType") int tradeType, @Field("statusCode") String statusCode);

    /**
     * 获取银行卡信息
     */
    @GET("userCenter/getBank")
    Single<ResultData> getBankCard(@Query("username") String username, @Query("userId") String userId);

    /**
     * 绑定银行卡
     */
    @POST("userCenter/bindBank")
    @FormUrlEncoded
    Single<ResultData> bindBankCard(@Field("") BankVo bankVo);
}
