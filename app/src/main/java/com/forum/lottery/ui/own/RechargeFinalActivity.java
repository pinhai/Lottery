package com.forum.lottery.ui.own;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.model.PayResultModel;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.QRCode;
import com.forum.lottery.utils.ScreenUtils;

/**
 * Created by admin on 2017/5/31.
 */

public class RechargeFinalActivity extends BaseActivity implements View.OnClickListener {

    private PayResultModel payResultModel;

    private TextView tv_payNo, tv_payMoneyCount;
    private ImageView iv_erweima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recharge_final);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        findView(R.id.img_back).setOnClickListener(this);
        tv_payNo = findView(R.id.tv_payNo);
        tv_payMoneyCount = findView(R.id.tv_payMoneyCount);
        tv_payNo.setText("订 单 号：" + payResultModel.getPaymentNo());
        tv_payMoneyCount.setText("充值金额：" + payResultModel.getTradeMoney());

        iv_erweima = findView(R.id.iv_erweima);
        setErweima();

    }

    @Override
    protected void initData() {
        payResultModel = (PayResultModel) getIntent().getSerializableExtra("paymodel");
        if(payResultModel == null){
            finish();
            return;
        }
    }

    //充值二维码
    private void setErweima() {
        String url = payResultModel.getUrlCode();
        if(url != null){
            iv_erweima.setImageBitmap(QRCode.createQRCode(url, ScreenUtils.dp2px(200)));

        }else {
            toast("二维码下载失败，请重试");
//            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
