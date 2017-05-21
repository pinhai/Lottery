package com.forum.lottery.ui.trend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forum.lottery.R;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.ui.TabBaseFragment;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TrendFragment extends TabBaseFragment {
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

    }

    @Override
    protected void initData() {

    }
}
