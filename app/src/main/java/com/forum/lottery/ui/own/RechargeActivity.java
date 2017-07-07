package com.forum.lottery.ui.own;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.model.PayResultModel;
import com.forum.lottery.model.RefreshMoneyModel;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.utils.CashierInputFilter;
import com.forum.lottery.utils.ScreenUtils;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 充值
 * Created by admin on 2017/5/30.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back, iv_assistant;
    private TextView tv_username, tv_balance;
    private ImageView tv_refreshMoney;
    private EditText et_money;
    private Button btn_submit;
    private RadioGroup rg_pay;
    private RadioButton rb_payWX, rb_payZFB, rb_payZXZF;
    private TextView tv_payPrompt;

    private PopupWindow pw_assistant;

    private UserVO userVO;
    private String balance;
    private String payWay = "0"; //充值方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recharge);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        userVO = getIntent().getParcelableExtra("user");
        balance = getIntent().getStringExtra("balance");
        if(userVO == null || balance == null){
            finish();
            return;
        }

        initAssistantPopup();
        img_back = findView(R.id.img_back);
        img_back.setOnClickListener(this);
        iv_assistant = findView(R.id.iv_assistant);
        iv_assistant.setOnClickListener(this);
        et_money = findView(R.id.et_money);
        InputFilter[] filters={new CashierInputFilter()};
        et_money.setFilters(filters);
        tv_balance = findView(R.id.tv_balance);
        tv_username = findView(R.id.tv_username);
        tv_balance.setText(balance);
        tv_username.setText(userVO.getAccount());
        tv_refreshMoney = findView(R.id.tv_refreshMoney);
        tv_refreshMoney.setOnClickListener(this);
        btn_submit = findView(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        tv_payPrompt = findView(R.id.tv_payPrompt);
        rg_pay = findView(R.id.rg_pay);
        rg_pay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_payWX:
                        tv_payPrompt.setText(R.string.pay_prompt_wx);
                        payWay = "0";
                        break;
                    case R.id.rb_payZFB:
                        tv_payPrompt.setText(R.string.pay_prompt_zfb);
                        payWay = "1";
                        break;
                    case R.id.rb_payZXZF:
                        payWay = "2";
                        break;
                }
            }
        });
        rg_pay.check(R.id.rb_payWX);

    }

    @Override
    protected void initData() {

    }

    private void initAssistantPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_recharge_assistant, null);
        view.findViewById(R.id.tv_rechargeRecord).setOnClickListener(this);
        view.findViewById(R.id.tv_kefu).setOnClickListener(this);
        // 创建PopupWindow对象
        pw_assistant = new PopupWindow(view, ScreenUtils.dp2px(130), android.view.ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pw_assistant.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pw_assistant.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pw_assistant.setFocusable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_assistant:
                pw_assistant.showAsDropDown(iv_assistant, -180, 0);
                break;
            case R.id.tv_rechargeRecord:
                Intent intent = new Intent(RechargeActivity.this, RechargeRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_kefu:
                break;
            case R.id.tv_refreshMoney:
                refreshMoney();
                break;
            case R.id.btn_submit:
                toRecharge();
                break;
        }
    }

    //充值
    private void toRecharge() {
        String money = et_money.getText().toString().trim();
        if(TextUtils.isEmpty(money)){
            toast(R.string.input_recharge_count);
            return;
        }

        showProgressDialog(false);
        createHttp(UserService.class)
                .recharge(0, payWay, Float.parseFloat(money))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<PayResultModel>() {
                    @Override
                    public void onSuccess(PayResultModel value) {
                        dismissProgressDialog();
                        if(value != null && value.getResult().contains("SUCCESS")){
                            Intent intent = new Intent(RechargeActivity.this, RechargeFinalActivity.class);
                            intent.putExtra("paymodel", value);
                            startActivity(intent);
                        }else{
                            toast("充值失败");
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        dismissProgressDialog();
                        toast(R.string.connection_failed);
                    }
                });

    }

    private void refreshMoney() {
        if(AccountManager.getInstance().isLogin()){
            UserVO userVO = AccountManager.getInstance().getUser();
            showIndeterminateDialog();
            createHttp(UserService.class)
                    .refreshMoney(userVO.getAccount())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<RefreshMoneyModel>() {
                        @Override
                        public void onSuccess(RefreshMoneyModel value) {
                            dismissIndeterminateDialog();
                            if(value!=null && value.getStatus().equals("1")){
                                balance = value.getBalance();
                                tv_balance.setText(value.getBalance());
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
}
