package com.forum.lottery.ui.buy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BettedDetailAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.BetResult;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.model.BetSubmitModel;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.LotteryUtils;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 下注最终界面-查看下的注列表
 * Created by Administrator on 2017/5/25 0025.
 */

public class BuyLotteryFinalActivity extends BaseActionBarActivity implements View.OnClickListener {

    private LotteryVO lotteryVO;
    private List<BetDetailModel> betDetailModels;  //下的注
    private ListView lv_bettedLottery;
    private BettedDetailAdapter adapter;

    private Button btn_backAddBet, btn_addMachineSelect;
    private Button btn_clear, btn_bet;
    private TextView tv_betCount, tv_betMoney;

    private int betTotalCount;
    private float betTotalMoney;

    private BetDetailModel tempBetDetail;  //临时

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
        setActionbarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinishDialog();
            }
        });

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
        lotteryVO = (LotteryVO) getIntent().getSerializableExtra("lottery");
        betDetailModels = (List<BetDetailModel>) getIntent().getSerializableExtra("betDetails");
        tempBetDetail = betDetailModels.get(0);
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
//        String requestJson = new Gson().toJson(new BetSubmitModel(betDetailModels));
        String requestJson = new Gson().toJson(betDetailModels);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),requestJson);
        showProgressDialog(false);
        createHttp(LotteryService.class)
                .bet(body)
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
                            if(value.getRet_msg() != null){
                                toast(value.getRet_msg());
                            }else{
                                toast(getString(R.string.bet_failed));
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable error){
                        dismissProgressDialog();
                        toast(getString(R.string.connection_failed));
                    }
                });
    }

    /**
     * 机选一注
     */
    private void selectOneBetByMachine(){
        BetDetailModel item;
        BetDetailModel temp;
        if(betDetailModels.size() > 0){
            temp = betDetailModels.get(0);
        }else{
            temp = tempBetDetail;
        }
        item = LotteryUtils.selectByMachineFromAddition2(temp);
        betDetailModels.add(item);
        adapter.notifyDataSetChanged();
        getTotalBet();
    }

    private void showFinishDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("返回将遗失所选号码，是否返回？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == 201){
            List<BetDetailModel> backBetData = (List<BetDetailModel>) data.getSerializableExtra("backBetData");
            if(backBetData != null){
                betDetailModels.addAll(backBetData);
                adapter.notifyDataSetChanged();
                getTotalBet();
            }
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_backAddBet:
                Intent intent = new Intent(BuyLotteryFinalActivity.this, BuyLotteryActivity.class);
                intent.putExtra("lottery", lotteryVO);
                intent.putExtra("backAddBet", true);
                startActivityForResult(intent, 101);
                break;
            case R.id.btn_addMachineSelect:
                // 机选添加
                selectOneBetByMachine();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                showFinishDialog();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
