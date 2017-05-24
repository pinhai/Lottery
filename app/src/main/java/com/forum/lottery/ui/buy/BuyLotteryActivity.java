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
import com.forum.lottery.event.BuyLotteryCheckChangeEvent;
import com.forum.lottery.model.BetItemModel;
import com.forum.lottery.model.BetListItemModel;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.ui.BaseBetFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        EventBus.getDefault().register(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        betFragment = DingWeiDanSelectFragment.newInstance(onCheckedListener);
        betFragment = LotterySelectFragment.newInstance(data);
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
        loadData();

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

    private List<BetListItemModel> data;
    private void loadData() {
        data = new ArrayList<>();
        List<BetItemModel> itemIntenal1 = new ArrayList<>();
        List<BetItemModel> itemIntenal2 = new ArrayList<>();
        List<BetItemModel> itemIntenal3 = new ArrayList<>();
        List<BetItemModel> itemIntenal4 = new ArrayList<>();
        List<BetItemModel> itemIntenal5 = new ArrayList<>();
        for(int i=0; i<10; i++){
            itemIntenal1.add(new BetItemModel(i+"", false));
            itemIntenal2.add(new BetItemModel(i+"", false));
            itemIntenal3.add(new BetItemModel(i+"", false));
            itemIntenal4.add(new BetItemModel(i+"", false));
            itemIntenal5.add(new BetItemModel(i+"", false));
        }

        BetListItemModel listitem1 = new BetListItemModel();
        listitem1.setLabel("万位");
        listitem1.setBetItems(itemIntenal1);
        BetListItemModel listitem2 = new BetListItemModel();
        listitem2.setLabel("千位");
        listitem2.setBetItems(itemIntenal2);
        BetListItemModel listitem3 = new BetListItemModel();
        listitem3.setLabel("百位");
        listitem3.setBetItems(itemIntenal3);
        BetListItemModel listitem4 = new BetListItemModel();
        listitem4.setLabel("十位");
        listitem4.setBetItems(itemIntenal4);
        BetListItemModel listitem5 = new BetListItemModel();
        listitem5.setLabel("个位");
        listitem5.setBetItems(itemIntenal5);

        data.add(listitem1);
        data.add(listitem2);
        data.add(listitem3);
        data.add(listitem4);
        data.add(listitem5);


    }

    private BetSelectAdapter.OnCheckedListener onCheckedListener = new BetSelectAdapter.OnCheckedListener() {
        @Override
        public void onCheckedChanged(boolean isChecked) {
            changeBetCount(isChecked);
        }
    };

    @Subscribe
    public void selectLotteryBetEvent(BuyLotteryCheckChangeEvent event){
        int count = 0;
        for(int i=0; i<data.size(); i++){
//            List<Boolean> item = selectedsALl.get(i);
            List<BetItemModel> item = data.get(i).getBetItems();
            for(int j=0; j<item.size(); j++){
                if(item.get(j).isChecked()){
                    ++count;
                }
            }
        }
        tv_betCount.setText(count + "");
        tv_betMoney.setText((count*2) + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                clearBet();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
