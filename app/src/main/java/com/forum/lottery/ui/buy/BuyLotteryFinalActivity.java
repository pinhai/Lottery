package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BettedDetailAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.BetResult;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.ui.BaseActivity;

import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

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

    private int betTotalCount;
    private float betTotalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_buy_lottery_final);

        initData();
        initView();
        getTotalBet();
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
        btn_clear = findView(R.id.btn_clear);
        btn_bet = findView(R.id.btn_bet);
        btn_clear.setOnClickListener(this);
        btn_bet.setOnClickListener(this);

    }

    @Override
    protected void initData(){
        betDetailModels = (List<BetDetailModel>) getIntent().getSerializableExtra("betDetails");
        adapter = new BettedDetailAdapter(this, betDetailModels, new BettedDetailAdapter.OnDeleteItemListener() {
            @Override
            public void itemDelete(){
                getTotalBet();
            }
        });

    }

    private void getTotalBet(){
        betTotalCount = 0;
        betTotalMoney = 0;
        for(BetDetailModel item : betDetailModels){
            betTotalCount += item.getBuyCount();
            betTotalMoney += item.getBuyCount()*item.getUnitPrice();
        }
        tv_betCount.setText(betTotalCount + "");
        tv_betMoney.setText(betTotalMoney + "");
    }

    private void bet(){
        if(betDetailModels == null || betDetailModels.size() == 0){
            toast("请先下注");
            return;
        }
        showProgressDialog(false);
        createHttp(LotteryService.class)
                .bet(betDetailModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<BetResult>() {
                    @Override
                    public void onSuccess(BetResult value){
                        dismissProgressDialog();
                        if(value.getRet_code().equals("200")){
                            toast(getString(R.string.buy_lottery_successful));
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            toast(getString(R.string.bet_failed));
                        }

                    }

                    @Override
                    public void onError(Throwable error){
                        dismissProgressDialog();
                        toast(getString(R.string.connection_failed));
                    }
                });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_backAddBet:
                finish();
                break;
            case R.id.btn_addMachineSelect:
                //// TODO: 2017/5/26 0026 机选添加

                break;
            case R.id.btn_clear:
                betDetailModels.clear();
                adapter.notifyDataSetChanged();
                getTotalBet();
                break;
            case R.id.btn_bet:
                bet();
                break;
        }
    }

}
