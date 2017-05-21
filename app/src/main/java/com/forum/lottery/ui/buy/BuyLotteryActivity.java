package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BetSelectAdapter;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.ui.BaseBetFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 买彩票界面
 * Created by Administrator on 2017/5/2.
 */

public class BuyLotteryActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_betCount, tv_betMoney;
    private Button btn_clear, btn_bet;

    private BaseBetFragment betFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_lottery);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        betFragment = DingWeiDanSelectFragment.newInstance(onCheckedListener);
        ft.add(R.id.rl_container, betFragment);
        ft.commit();

        tv_betCount = findView(R.id.tv_betCount);
        tv_betMoney = findView(R.id.tv_betMoney);
        btn_bet = findView(R.id.btn_bet);
        btn_clear = findView(R.id.btn_clear);
        btn_clear.setOnClickListener(this);

    }

    @Override
    protected void initData() {


    }

    private void changeBetCount(boolean isChecked) {
        int count = Integer.parseInt(tv_betCount.getText().toString().trim());
        count = isChecked ? count+1 : count-1;
        tv_betCount.setText(count + "");
        tv_betMoney.setText((count*2) + "");
    }

    private void clearBet(){
//        tv_betCount.setText(String.valueOf(0));
//        tv_betMoney.setText(String.valueOf(0));
        betFragment.clearCheckedBetting();
    }

    private BetSelectAdapter.OnCheckedListener onCheckedListener = new BetSelectAdapter.OnCheckedListener() {
        @Override
        public void onCheckedChanged(boolean isChecked) {
            changeBetCount(isChecked);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                clearBet();
                break;
        }
    }
}
