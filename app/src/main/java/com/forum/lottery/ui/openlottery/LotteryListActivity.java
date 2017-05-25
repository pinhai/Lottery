package com.forum.lottery.ui.openlottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.LotteryListAdapter;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.ui.BaseActionBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class LotteryListActivity extends BaseActionBarActivity {
    private ListView listLottery;
    private int type;
    private String title;
    public static void startActivity(Context context, LotteryVO lotteryVO){
        Intent intent = new Intent(context, LotteryListActivity.class);
        intent.putExtra("title", "重庆时时彩");
        intent.putExtra("type", 1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_list);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        listLottery = findView(R.id.list_lottery);

        loadLotteryList();
    }

    @Override
    protected void initData() {
        setTitle(title);
    }

    @Override
    protected void initParams(Bundle bundle) {
        super.initParams(bundle);
        title = bundle.getString("title");
        type = bundle.getInt("type", 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",title);
        outState.putInt("type", type);
    }

    private void loadLotteryList(){
        List<LotteryVO> lotteryVOs = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            lotteryVOs.add(new LotteryVO());
        }
        LotteryListAdapter lotteryListAdapter = new LotteryListAdapter(this, lotteryVOs);
        lotteryListAdapter.setHideTitle(true);
        lotteryListAdapter.setViewType(type);
        listLottery.setAdapter(lotteryListAdapter);
    }
}
