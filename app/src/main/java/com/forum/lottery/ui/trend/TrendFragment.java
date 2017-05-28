package com.forum.lottery.ui.trend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.TrendAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.event.RefreshLotteryListEvent;
import com.forum.lottery.model.TrendModel;
import com.forum.lottery.ui.TabBaseFragment;

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

    private LotteryVO currentLotteryVO;

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
        EventBus.getDefault().register(this);
        trendModels = new ArrayList<>();
        trendAdapter = new TrendAdapter(getActivity(), trendModels, TrendAdapter.Weishu.GE);
        gv_trend = findView(R.id.gv_trend);
        gv_trend.setAdapter(trendAdapter);


    }

    private void getTrendData() {
        createHttp(LotteryService.class)
                .getTrendData(currentLotteryVO.getLotteryid(), 20)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        if(value != null && value.getRecords() != null){
                            trendModels.clear();
                            trendModels.addAll(value.getRecords());
                            trendAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                    }
                });
    }

    @Override
    protected void initData() {

    }

    @Subscribe
    public void getLotteryList(LotteryListTickEvent event){
        if(currentLotteryVO == null){
            currentLotteryVO = event.data.get(0);
            getTrendData();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
