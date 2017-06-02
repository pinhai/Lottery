package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.forum.lottery.MainActivity;
import com.forum.lottery.R;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.event.RefreshLotteryListEvent;
import com.forum.lottery.event.RefreshLotteryListResultEvent;
import com.forum.lottery.ui.TabBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/4/21.
 */

public class BuyFragment extends TabBaseFragment implements View.OnClickListener {
    private int curCheckId = -1;
    private int prepareCheckId = R.id.radio_all;
    private ImageView iv_refresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public boolean initNet() {
        return true;
    }

    @Override
    public String getTitle() {
        return MyApplication.getInstance().getString(R.string.buy_title);
    }

    @Override
    public int getIcon() {
        return R.drawable.nav_2_selector;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        RadioGroup radioBuyTab = findView(R.id.radio_buy_tab);
        radioBuyTab.check(prepareCheckId);
        changeTab(prepareCheckId);
        radioBuyTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                changeTab(i);
            }
        });
        iv_refresh = findView(R.id.iv_refresh);
        iv_refresh.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initParams(Bundle bundle) {
        super.initParams(bundle);
        prepareCheckId = bundle.getInt("curCheck", R.id.radio_all);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curCheck", curCheckId);
    }

    @Subscribe
    public void getRefreshResult(RefreshLotteryListResultEvent event){
        ((MainActivity)getActivity()).dismissIndeterminateDialog();
    }

    private void changeTab(int radioId){
        if(radioId == curCheckId)
            return;
        FragmentManager fm = getFragmentManager();
        String checkName = makeFragmentName(radioId);
        Fragment checkFragment = fm.findFragmentByTag(checkName);
        FragmentTransaction transaction = fm.beginTransaction();
        if(checkFragment == null){
            transaction.add(R.id.frame_buy_container, buildFragment(radioId), checkName);
        }else{
            transaction.show(checkFragment);
        }
        if(curCheckId != -1){
            Fragment curCheckFragment = fm.findFragmentByTag(makeFragmentName(curCheckId));
            if(curCheckFragment != null){
                transaction.hide(curCheckFragment);
            }
        }
        transaction.commit();
        curCheckId = radioId;
    }

    private String makeFragmentName(int radioId) {
        return "changeTab:" + radioId;
    }

    private Fragment buildFragment(int radioId){
        return new BuyListFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_refresh:
                ((MainActivity)getActivity()).showIndeterminateDialog();
                EventBus.getDefault().post(new RefreshLotteryListEvent(0));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
