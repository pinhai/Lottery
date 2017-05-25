package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.widget.ListView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BettedDetailAdapter;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.ui.BaseActivity;

import java.util.List;

/**
 * 下注最终界面-查看下的注列表
 * Created by Administrator on 2017/5/25 0025.
 */

public class BuyLotteryFinalActivity extends BaseActionBarActivity {

    private List<BetDetailModel> betDetailModels;  //下的注
    private ListView lv_bettedLottery;
    private BettedDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_buy_lottery_final);

        initData();
        initView();
    }

    @Override
    protected void initView(){
        setTitle("投注列表");
    }

    @Override
    protected void initData(){
        betDetailModels = (List<BetDetailModel>) getIntent().getSerializableExtra("betDetails");
        adapter = new BettedDetailAdapter(this, betDetailModels);

    }
}
