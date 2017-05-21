package com.forum.lottery.ui.own;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.ui.TabBaseFragment;

/**
 * Created by Administrator on 2017/4/21.
 */

public class OwnFragment extends TabBaseFragment implements View.OnClickListener{

    private TextView tv_rechargeRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_own, container, false);
    }
    @Override
    public boolean initNet() {
        return true;
    }

    @Override
    public String getTitle() {
        return MyApplication.getInstance().getString(R.string.own_title);
    }

    @Override
    public int getIcon() {
        return R.drawable.nav_5_selector;
    }

    @Override
    protected void initView() {
        tv_rechargeRecord = findView(R.id.tv_rechargeRecord);
        tv_rechargeRecord.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_rechargeRecord:
                Intent intent = new Intent(getActivity(), RechargeRecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
