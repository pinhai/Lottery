package com.forum.lottery.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forum.lottery.R;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.ui.login.LoginActivity;
import com.forum.lottery.ui.login.RegisterActivity;

/**
 * Created by Administrator on 2017/4/21.
 */

public class HomeFragment extends TabBaseFragment {
    public static final int LOGIN_CODE = 100;
    public static final int REGISTER_CODE = 101;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public boolean initNet() {
        return true;
    }

    @Override
    public String getTitle() {
        return MyApplication.getInstance().getString(R.string.home_title);
    }

    @Override
    public int getIcon() {
        return R.drawable.nav_1_selector;
    }

    @Override
    protected void initView() {
        findView(R.id.txt_login).setOnClickListener(onClickListener);
        findView(R.id.txt_register).setOnClickListener(onClickListener);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_CODE && resultCode == Activity.RESULT_OK){
            toast("登录成功");
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.txt_login:
                    LoginActivity.startActivity(HomeFragment.this, LOGIN_CODE);
                    break;
                case R.id.txt_register:
                    RegisterActivity.startActivity(getActivity(), REGISTER_CODE);
                    break;
            }
        }
    };
}
