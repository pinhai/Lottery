package com.forum.lottery.ui.buy;

import android.os.Bundle;

import com.forum.lottery.R;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.ui.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class BuyLotteryFinalActivity extends BaseActivity {

    private List<BetDetailModel> betDetailModels;  //下的注

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_buy_lottery_final);

    }

    @Override
    protected void initView(){

    }

    @Override
    protected void initData(){
        betDetailModels = (List<BetDetailModel>) getIntent().getSerializableExtra("betDetails");

    }
}
