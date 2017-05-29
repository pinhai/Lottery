package com.forum.lottery.ui.own;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.RefreshMoneyModel;
import com.forum.lottery.api.UserService;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.event.LoginEvent;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.utils.AccountManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/21.
 */

public class OwnFragment extends TabBaseFragment implements View.OnClickListener{

    private TextView tv_rechargeRecord, tv_betRecord, tv_winingRecord, tv_accountDetail, tv_drawMoneyRecord;
    private TextView tv_username, tv_refreshMoney, tv_balance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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
        tv_username = findView(R.id.tv_username);
        tv_refreshMoney = findView(R.id.tv_refreshMoney);
        tv_refreshMoney.setOnClickListener(this);
        tv_balance = findView(R.id.tv_balance);
        tv_betRecord = findView(R.id.tv_betRecord);
        tv_betRecord.setOnClickListener(this);
        tv_winingRecord = findView(R.id.tv_winingRecord);
        tv_winingRecord.setOnClickListener(this);
        tv_accountDetail = findView(R.id.tv_accountDetail);
        tv_accountDetail.setOnClickListener(this);
        tv_drawMoneyRecord = findView(R.id.tv_drawMoneyRecord);
        tv_drawMoneyRecord.setOnClickListener(this);

        setUserInfo();
    }

    @Override
    protected void initData() {

    }

    @Subscribe
    public void loginEvent(LoginEvent event){
        setUserInfo();
    }

    private void setUserInfo(){
        if(AccountManager.getInstance().isLogin()){
            UserVO userVO = AccountManager.getInstance().getUser();
            tv_username.setText(userVO.getAccount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_rechargeRecord:
                Intent intent = new Intent(getActivity(), RechargeRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_refreshMoney:
                refreshMoney();
                break;
            case R.id.tv_betRecord:
                Intent intent2 = new Intent(getActivity(), BetRecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_winingRecord:
                Intent intent3 = new Intent(getActivity(), WinningRecordActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_accountDetail:
                Intent intent4 = new Intent(getActivity(), AccountDetailActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_drawMoneyRecord:
                Intent intent5 = new Intent(getActivity(), DrawMoneyRecordActivity.class);
                startActivity(intent5);
                break;

        }
    }

    private void refreshMoney() {
        if(AccountManager.getInstance().isLogin()){
            UserVO userVO = AccountManager.getInstance().getUser();
            createHttp(UserService.class)
                    .refreshMoney(userVO.getAccount())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<RefreshMoneyModel>() {
                        @Override
                        public void onSuccess(RefreshMoneyModel value) {
                            if(value!=null && value.getStatus().equals("1")){
                                tv_balance.setText("￥" + value.getBalance());
                            }else {
                                toast(R.string.connection_failed);
                            }
                        }

                        @Override
                        public void onError(Throwable error) {
                            toast(R.string.connection_failed);
                        }
                    });
        }else{
            toast(getString(R.string.login_prompt));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
