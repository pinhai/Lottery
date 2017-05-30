package com.forum.lottery.ui.own;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.forum.lottery.R;
import com.forum.lottery.model.BankInfoModel;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.CashierInputFilter;

/**
 * 提款
 * Created by admin_h on 2017/5/30.
 */

public class DrawMoneyActivity extends BaseActivity implements View.OnClickListener{

    private BankInfoModel bankInfo;

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

        //// TODO: 2017/5/30 提现

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
