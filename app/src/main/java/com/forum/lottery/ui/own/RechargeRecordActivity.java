package com.forum.lottery.ui.own;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.view.MyRadioGroup;

/**
 * Created by admin_h on 2017/5/21.
 */

public class RechargeRecordActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_titleBar;
    private TextView tv_recordType;

    private MyRadioGroup rg_recordType;
    private RadioButton rb_all, rb_checkPending, rb_deposited, rb_canceled;
    private PopupWindow pw_recordType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recharge_record);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        initPopupWindow();
        tv_recordType = findView(R.id.tv_recordType);
        tv_recordType.setOnClickListener(this);
        rl_titleBar = findView(R.id.rl_titleBar);

    }

    @Override
    protected void initData() {

    }

//    private void showRecordTypePopup() {
//        View contentView = LayoutInflater.from(this).inflate(R.layout.view_record_type, null);
//        pw_recordType = new PopupWindow(contentView);
//        pw_recordType.showAsDropDown(rl_titleBar);
//
//    }

    private void initPopupWindow() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.view_record_type, null);
        pw_recordType = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        initPopupView(popupView);
        pw_recordType.setTouchable(true);
        pw_recordType.setOutsideTouchable(true);
        pw_recordType.setBackgroundDrawable(new BitmapDrawable());
//        pw_recordType.update();
//        pw_recordType.getContentView().setFocusableInTouchMode(true);
//        pw_recordType.getContentView().setFocusable(true);
//        pw_recordType.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void initPopupView(View popupView) {
        rg_recordType = (MyRadioGroup) popupView.findViewById(R.id.rg_recordType);
        rb_all = (RadioButton) popupView.findViewById(R.id.rb_all);
        rb_checkPending = (RadioButton) popupView.findViewById(R.id.rb_checkPending);
        rb_canceled = (RadioButton) popupView.findViewById(R.id.rb_canceled);
        rb_deposited = (RadioButton) popupView.findViewById(R.id.rb_deposited);
        rg_recordType.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_all:
                        rb_all.setTextColor(getResources().getColor(R.color.red));
                        break;
                }
            }

        });
//        rb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                rb_all.setTextColor(getResources().getColor(R.color.red));
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_recordType:
                if(pw_recordType.isShowing()){
                    pw_recordType.dismiss();
                }else{
                    pw_recordType.showAsDropDown(rl_titleBar);
                }
                break;
        }

    }

}
