package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class BuyLotteryFinalActivity extends BaseActionBarActivity implements View.OnClickListener {

    private List<BetDetailModel> betDetailModels;  //下的注
    private ListView lv_bettedLottery;
    private BettedDetailAdapter adapter;

    private Button btn_backAddBet, btn_addMachineSelect;
    private Button btn_clear, btn_bet;
    private TextView tv_betCount, tv_betMoney;

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

        lv_bettedLottery = findView(R.id.lv_bettedLottery);
        lv_bettedLottery.setAdapter(adapter);

        btn_backAddBet = findView(R.id.btn_backAddBet);
        btn_addMachineSelect = findView(R.id.btn_addMachineSelect);
        btn_backAddBet.setOnClickListener(this);
        btn_addMachineSelect.setOnClickListener(this);
        tv_betCount = findView(R.id.tv_betCount);
        tv_betMoney = findView(R.id.tv_betMoney);

    }

    @Override
    protected void initData(){
        betDetailModels = (List<BetDetailModel>) getIntent().getSerializableExtra("betDetails");
        adapter = new BettedDetailAdapter(this, betDetailModels);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){

        }
    }
}
