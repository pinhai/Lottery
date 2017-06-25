package com.forum.lottery.ui.trend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.TrendAdapter;
import com.forum.lottery.adapter.TrendWeishuAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.model.TrendModel;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.ui.buy.BuyLotteryActivity;
import com.forum.lottery.utils.LotteryUtils;
import com.forum.lottery.view.ActionMenuPopup;
import com.forum.lottery.view.MyGridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TrendFragment extends TabBaseFragment {

    private GridView gv_trend;
    private TrendAdapter trendAdapter;
    private List<TrendModel> trendModels;
    private String currentWeishu;

    private MyGridView mgv_weishu;
    private TrendWeishuAdapter trendWeishuAdapter;
    private List<String> weishuStrings;

    private TextView tv_lotteryName;
    private Button btn_bet;
    private ImageButton ib_lotteryType;

    private List<LotteryVO> lotteryVOs;
    private LotteryVO currentLotteryVO;

    private ActionMenuPopup pw_lottery;
    private List<String> popupMenus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trend, container, false);
    }
    @Override
    public boolean initNet() {
        return true;
    }

    @Override
    public String getTitle() {
        return MyApplication.getInstance().getString(R.string.trend_title);
    }

    @Override
    public int getIcon() {
        return R.drawable.nav_4_selector;
    }

    @Override
    protected void initView() {
//        Intent intent = new Intent(getActivity(), TrendActivity.class);
//        startActivity(intent);

        findView(R.id.img_back).setVisibility(View.GONE);

        EventBus.getDefault().register(this);
        trendModels = new ArrayList<>();
        currentWeishu = getString(R.string.ge);
        trendAdapter = new TrendAdapter(getActivity(), trendModels, currentWeishu);
        gv_trend = findView(R.id.gv_trend);
        gv_trend.setAdapter(trendAdapter);

        ib_lotteryType = findView(R.id.ib_lotteryType);
        ib_lotteryType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw_lottery != null){
                    pw_lottery.show(ib_lotteryType);
                }
            }
        });

        weishuStrings = new ArrayList<>();
//        weishuStrings.add(getString(R.string.bai));
//        weishuStrings.add(getString(R.string.shi));
//        weishuStrings.add(getString(R.string.ge));
        trendWeishuAdapter = new TrendWeishuAdapter(getActivity(), weishuStrings, new TrendWeishuAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(String value, int position, int weishuCount) {
                currentWeishu = value;
                trendAdapter.setWeishu(currentWeishu, weishuCount);
                trendAdapter.notifyDataSetChanged();
            }
        });
        mgv_weishu = findView(R.id.mgv_weishu);
        mgv_weishu.setAdapter(trendWeishuAdapter);

        tv_lotteryName = findView(R.id.tv_lotteryName);
        btn_bet = findView(R.id.btn_bet);
        btn_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLotteryVO != null){
                    Intent intent = new Intent(getActivity(), BuyLotteryActivity.class);
                    intent.putExtra("lottery", currentLotteryVO);
                    startActivity(intent);
                }else{
                    toast(R.string.connection_failed);
                }

            }
        });

        popupMenus = new ArrayList<>();

    }

    private void getTrendData() {
        createHttp(LotteryService.class)
                .getTrendData(currentLotteryVO.getLotteryid(), 20, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        if(value != null && value.getRecords() != null){
                            trendModels.clear();
                            trendModels.addAll(value.getRecords());
                            trendAdapter.notifyDataSetChanged();
                            initWeishuGrid();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                    }
                });
    }

    private void initWeishuGrid() {
        if(trendModels.size() == 0){
            return;
        }
        int count = trendModels.get(0).getAllcode().length;
        weishuStrings = LotteryUtils.getTrendWeishuMenu(getActivity(), count);
        trendWeishuAdapter.setData(weishuStrings);
    }

    @Override
    protected void initData() {

    }

    @Subscribe
    public void getLotteryList(LotteryListTickEvent event){
        if(currentLotteryVO == null){
            lotteryVOs = event.data;
            getLotteryTypePopupMenu();
            currentLotteryVO = event.data.get(0);
            getTrendData();
        }

    }

    private void getLotteryTypePopupMenu() {
        for(LotteryVO item : lotteryVOs){
            popupMenus.add(item.getLotteryName());
        }

        pw_lottery = new ActionMenuPopup(getActivity(), popupMenus, new ActionMenuPopup.OnItemCheckedListener() {
            @Override
            public void onCheck(String value, int position, boolean manual) {
                if(manual){
                    pw_lottery.dismiss();
                }
                currentLotteryVO = lotteryVOs.get(position);
                currentWeishu = getString(R.string.ge);
                getTrendData();
                tv_lotteryName.setText(currentLotteryVO.getLotteryName());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
