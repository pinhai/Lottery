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
import com.forum.lottery.view.ActionMenuPopup;
import com.forum.lottery.view.MyGridView;
import com.forum.lottery.view.MyRadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 充值记录
 * Created by admin_h on 2017/5/21.
 */

public class RechargeRecordActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_titleBar;
    private TextView tv_recordType;


//    private MyRadioGroup rg_recordType;
//    private RadioButton rb_all, rb_checkPending, rb_deposited, rb_canceled;
    private ActionMenuPopup pw_recordType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recharge_record);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        tv_recordType = findView(R.id.tv_recordType);
        tv_recordType.setOnClickListener(this);
        rl_titleBar = findView(R.id.rl_titleBar);

    }

    @Override
    protected void initData() {
        List<String> menu = new ArrayList<>();
        menu.addAll(Arrays.asList(getResources().getStringArray(R.array.record_type)));
        pw_recordType = new ActionMenuPopup(this, menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_recordType:
                if(pw_recordType.isShowing()){
                    pw_recordType.dismiss();
                }else{
                    pw_recordType.show(rl_titleBar);
                }
                break;
        }

    }

}
