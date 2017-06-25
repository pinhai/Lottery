package com.forum.lottery.ui.own;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.model.RefreshMoneyModel;
import com.forum.lottery.api.UserService;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.event.LoginEvent;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.ui.home.HomeFragment;
import com.forum.lottery.ui.login.LoginActivity;
import com.forum.lottery.utils.AccountManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/21.
 */

public class OwnFragment extends TabBaseFragment implements View.OnClickListener{

    private TextView tv_recharge, tv_drawMoney;
    private TextView tv_rechargeRecord, tv_betRecord, tv_winingRecord, tv_accountDetail, tv_drawMoneyRecord, tv_personInfo;
    private TextView tv_username, tv_refreshMoney, tv_balance;
    private Button btn_logout;
    private TextView tv_modifyTradePsw, tv_modifyLoginPsw;
    private LinearLayout ll_user;

    private String balance;  //余额

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
        tv_recharge = findView(R.id.tv_recharge);
        tv_drawMoney = findView(R.id.tv_drawMoney);
        tv_recharge.setOnClickListener(this);
        tv_drawMoney.setOnClickListener(this);
        tv_personInfo = findView(R.id.tv_personInfo);
        tv_personInfo.setOnClickListener(this);
        btn_logout = findView(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
        tv_modifyTradePsw = findView(R.id.tv_modifyTradePsw);
        tv_modifyTradePsw.setOnClickListener(this);
        tv_modifyLoginPsw = findView(R.id.tv_modifyLoginPsw);
        tv_modifyLoginPsw.setOnClickListener(this);
        ll_user = findView(R.id.ll_user);
        ll_user.setOnClickListener(this);

        setUserInfo();
        refreshMoney();
    }

    @Override
    protected void initData() {

    }

    private void showPromptDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt)
                .setMessage("未收到消息")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    @Subscribe
    public void loginEvent(LoginEvent event){
        setUserInfo();
    }

    private void setUserInfo(){
        if(AccountManager.getInstance().isLogin()){
            refreshMoney();
            UserVO userVO = AccountManager.getInstance().getUser();
            tv_username.setText(userVO.getAccount());
            btn_logout.setVisibility(View.VISIBLE);
        }else{
            btn_logout.setVisibility(View.GONE);
            tv_username.setText("请登录");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_user:
                if(!AccountManager.getInstance().isLogin()){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.btn_logout:
                AccountManager.getInstance().logout();
                LoginActivity.startActivity(OwnFragment.this, HomeFragment.LOGIN_CODE);
                break;
            case R.id.tv_recharge:
                if(checkLogin()){
                    Intent intent6 = new Intent(getActivity(), RechargeActivity.class);
                    intent6.putExtra("user", AccountManager.getInstance().getUser());
                    intent6.putExtra("balance", balance);
                    startActivity(intent6);
                }
                break;
            case R.id.tv_drawMoney:
                drawMoney();
                break;
            case R.id.tv_rechargeRecord:
                if(checkLogin()){
                    Intent intent = new Intent(getActivity(), RechargeRecordActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_refreshMoney:
                refreshMoney();
                break;
            case R.id.tv_betRecord:
                if(checkLogin()){
                    Intent intent2 = new Intent(getActivity(), BetRecordActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.tv_winingRecord:
                if(checkLogin()){
                    Intent intent3 = new Intent(getActivity(), WinningRecordActivity.class);
                    startActivity(intent3);
                }
                break;
            case R.id.tv_accountDetail:
                if(checkLogin()){
                    Intent intent4 = new Intent(getActivity(), AccountDetailActivity.class);
                    startActivity(intent4);
                }
                break;
            case R.id.tv_drawMoneyRecord:
                if(checkLogin()){
                    Intent intent5 = new Intent(getActivity(), DrawMoneyRecordActivity.class);
                    startActivity(intent5);
                }
                break;
            case R.id.tv_personInfo:
                showPromptDialog();
                break;
            case R.id.tv_modifyLoginPsw:
                if(checkLogin()){
                    Intent intent6 = new Intent(getActivity(), ModifyLoginPswActivity.class);
                    startActivity(intent6);
                }
                break;
            case R.id.tv_modifyTradePsw:
                if(checkLogin()){
                    Intent intent6 = new Intent(getActivity(), ModifyTradePswActivity.class);
                    startActivity(intent6);
                }
                break;

        }
    }

    //提款
    private void drawMoney() {

        if(checkLogin()){
            showIndeterminateDialog();
            UserVO userVO = AccountManager.getInstance().getUser();
            createHttp(UserService.class)
                    .getBankCard(userVO.getAccount(), userVO.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<ResultData>() {
                        @Override
                        public void onSuccess(ResultData value) {
                            if(isShowIndeterminateDialog()){
                                dismissIndeterminateDialog();
                                if(value != null && value.getResult().equals("SUCCESS") && value.getBankInfo() != null){
                                    //绑定了银行卡
                                    Intent intent6 = new Intent(getActivity(), DrawMoneyActivity.class);
                                    intent6.putExtra("bankInfo", value.getBankInfo());
                                    startActivity(intent6);
                                }else {
                                    //未绑定银行卡
                                    Intent intent7 = new Intent(getActivity(), BindBankCardActivity.class);
                                    startActivity(intent7);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable error) {
                            toast(R.string.connection_failed);
                        }
                    });

        }

    }

    private boolean checkLogin() {
        if(!AccountManager.getInstance().isLogin()){
            toast(getString(R.string.login_prompt));
            return false;
        }
        return true;
    }

    private void refreshMoney() {
        if(AccountManager.getInstance().isLogin()){
            showIndeterminateDialog();
            UserVO userVO = AccountManager.getInstance().getUser();
            createHttp(UserService.class)
                    .refreshMoney(userVO.getAccount())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<RefreshMoneyModel>() {
                        @Override
                        public void onSuccess(RefreshMoneyModel value) {
                            dismissIndeterminateDialog();
                            if(value!=null && value.getStatus().equals("1")){
                                balance = value.getBalance();
                                tv_balance.setText("￥" + value.getBalance());
                            }else {
                                toast(R.string.connection_failed);
                            }
                        }

                        @Override
                        public void onError(Throwable error) {
                            dismissIndeterminateDialog();
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
