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
    private List<LotteryVO> lotteryVOs;
    private int type;
//    private String title;
    private LotteryVO lotteryVO;

    public static void startActivity(Context context, LotteryVO lotteryVO){
        Intent intent = new Intent(context, LotteryListActivity.class);
//        intent.putExtra("title", "重庆时时彩");
        intent.putExtra("type", 1);
        intent.putExtra("lottery", lotteryVO);
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

        lotteryVOs = new ArrayList<>();
        loadLotteryList();
    }

    @Override
    protected void initData() {
        setTitle(lotteryVO.getLotteryName());
    }

    @Override
    protected void initParams(Bundle bundle) {
        super.initParams(bundle);
//        title = bundle.getString("title");
        type = bundle.getInt("type", 0);
        lotteryVO = (LotteryVO) bundle.getSerializable("lottery");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",lotteryVO.getLotteryName());
        outState.putInt("type", type);
    }

    private void loadLotteryList(){
        for(int i = 0; i < 20; i++){
            lotteryVOs.add(lotteryVO);
        }
        LotteryListAdapter lotteryListAdapter = new LotteryListAdapter(this, lotteryVOs);
        lotteryListAdapter.setHideTitle(true);
        lotteryListAdapter.setViewType(type);
        listLottery.setAdapter(lotteryListAdapter);
    }
}
