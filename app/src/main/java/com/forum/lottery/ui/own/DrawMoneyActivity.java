package com.forum.lottery.ui.own;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.model.BankInfoModel;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.utils.CashierInputFilter;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 提款
 * Created by admin_h on 2017/5/30.
 */

public class DrawMoneyActivity extends BaseActivity implements View.OnClickListener{

    private BankInfoModel bankInfo;

    private TextView tv_bankNo;
    private EditText et_drawMoneyCount, et_password;
    private Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_draw_money);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        et_drawMoneyCount = findView(R.id.et_drawMoneyCount);
        InputFilter[] filters={new CashierInputFilter()};
        et_drawMoneyCount.setFilters(filters);
        et_password = findView(R.id.et_password);
        btn_submit = findView(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        tv_bankNo = findView(R.id.tv_bankNo);
        String bankName = bankInfo.getBankno();
        String showBankName = bankName.substring(0, 4) + "****" + bankName.substring(bankName.length()-4, bankName.length());
        tv_bankNo.setText(showBankName);

    }

    @Override
    protected void initData() {
        bankInfo = (BankInfoModel) getIntent().getSerializableExtra("bankInfo");
        if(bankInfo == null){
            finish();
            return;
        }

    }

    private void drawMoney() {
        if(!AccountManager.getInstance().isLogin()){
            toast(R.string.login_prompt);
            return;
        }

        String drawCount = et_drawMoneyCount.getText().toString().trim();
        if(TextUtils.isEmpty(drawCount)){
            toast(R.string.input_draw_money_count);
            return;
        }
        String psw = et_password.getText().toString().trim();
        if(TextUtils.isEmpty(psw)){
            toast(R.string.input_trade_psw);
            return;
        }
        if(psw.length() != 6){
            toast("请输入六位交易密码");
            return;
        }

        showProgressDialog(false);
        UserVO userVO = AccountManager.getInstance().getUser();
        createHttp(UserService.class)
                .drawMoneyApply(drawCount, psw, userVO.getAccount(), userVO.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        dismissProgressDialog();
                        if(value != null && value.getResult().equals("SUCCESS")){
                            toast("提交成功");
                            finish();
                        }else {
                            if(value.getMsg() != null){
                                toast(value.getMsg());
                            }else {
                                toast("提交失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        dismissProgressDialog();
                        toast("提交失败");
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                drawMoney();
                break;
        }
    }

}
