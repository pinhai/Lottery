package com.forum.lottery.ui.own;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.RechargeRecordAdapter;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.PageResult;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.model.TradeStreamVo;
import com.forum.lottery.model.TradeStreamVo;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.view.ActionMenuPopup;
import com.forum.lottery.view.MyGridView;
import com.forum.lottery.view.MyRadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 充值记录
 * Created by admin_h on 2017/5/21.
 */

public class RechargeRecordActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_titleBar;
    private TextView tv_recordType;
    private ImageView iv_refresh;

    private View loadMore;
    private TextView tv_loadMore;
    private ListView lv_record;
    private RechargeRecordAdapter adapter;
    private List<TradeStreamVo> data;
    private int page = 1;
    private static final int rows = 20; //每一页的行数

    private ActionMenuPopup pw_recordType;
    private String[] popupMenus;
    private String qureyType = null;  //查询类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recharge_record);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        popupMenus = getResources().getStringArray(R.array.recharge_record_type);
        List<String> menu = new ArrayList<>();
        menu.addAll(Arrays.asList(popupMenus));
        pw_recordType = new ActionMenuPopup(this, menu, popupMenuItemCheckListener);

        tv_recordType = findView(R.id.tv_recordType);
//        tv_recordType.setOnClickListener(this);
        rl_titleBar = findView(R.id.rl_titleBar);
        iv_refresh = findView(R.id.iv_refresh);
        iv_refresh.setOnClickListener(this);

        loadMore = LayoutInflater.from(this).inflate(R.layout.view_load_more, null);
        tv_loadMore = (TextView) loadMore.findViewById(R.id.tv_loadMore);
        tv_loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_loadMore.setText(R.string.loading);
                getRecordData(++page);
            }
        });
        lv_record = findView(R.id.lv_record);
        lv_record.addFooterView(loadMore);
        data = new ArrayList<>();
        adapter = new RechargeRecordAdapter(this, data);
        lv_record.setAdapter(adapter);
        lv_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        getRecordData(page);

    }

    @Override
    protected void initData() {
    }

    private void getRecordData(final int page){
        if(AccountManager.getInstance().isLogin()){
            showIndeterminateDialog();
            UserVO userVO = AccountManager.getInstance().getUser();
            createHttp(UserService.class)
                    .getRechargeRecord(userVO.getAccount(), page, rows, 0, qureyType)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<PageResult<TradeStreamVo>>() {
                        @Override
                        public void onSuccess(PageResult<TradeStreamVo> value) {
                            dismissIndeterminateDialog();
                            if(value != null){
                                List<TradeStreamVo> recordModels = value.getRows();
                                if(recordModels.size() == 0){
                                    tv_loadMore.setText(R.string.load_all);
                                }else{
                                    tv_loadMore.setText(R.string.load_more);
                                    if(page == 1){
                                        data.clear();
                                        toast(R.string.refresh_successful);
                                    }
                                    data.addAll(recordModels);
                                    adapter.notifyDataSetChanged();
                                }

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
        }
    }

    private ActionMenuPopup.OnItemCheckedListener popupMenuItemCheckListener = new ActionMenuPopup.OnItemCheckedListener() {
        @Override
        public void onCheck(String value, int position, boolean manual) {
            if(manual){
                pw_recordType.dismiss();
                if(value.equals(popupMenus[0])){
                    qureyType = null;
                }else if(value.equals(popupMenus[1])) {
                    qureyType = "0";
                }else if(value.equals(popupMenus[2])){
                    qureyType = "1";
                }else{
                    qureyType = "2";
                }
                page = 1;
                getRecordData(page);
            }
        }
    };

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
            case R.id.iv_refresh:
                page = 1;
                getRecordData(page);
                break;
        }

    }
}
