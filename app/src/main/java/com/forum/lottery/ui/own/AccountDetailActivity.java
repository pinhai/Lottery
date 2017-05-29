package com.forum.lottery.ui.own;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BetRecordAdapter;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.model.BetRecordModel;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.view.ActionMenuPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 账户明细
 * Created by admin_h on 2017/5/29.
 */

public class AccountDetailActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_titleBar;
    private TextView tv_recordType;
    private ImageView iv_refresh;

    private View loadMore;
    private TextView tv_loadMore;
    private ListView lv_record;
    private BetRecordAdapter adapter;
    private List<BetRecordModel> data;
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
        List<String> menu = new ArrayList<>();
        menu.addAll(Arrays.asList(getResources().getStringArray(R.array.record_type)));
        pw_recordType = new ActionMenuPopup(this, menu);

        tv_recordType = findView(R.id.tv_recordType);
        tv_recordType.setOnClickListener(this);
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
        adapter = new BetRecordAdapter(this, data);
        lv_record.setAdapter(adapter);
        lv_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        getRecordData(page);

    }

    private ActionMenuPopup.OnItemCheckedListener popupMenuItemCheckListener = new ActionMenuPopup.OnItemCheckedListener() {
        @Override
        public void onCheck(String value, int position, boolean manual) {
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
    };

    @Override
    protected void initData() {
    }

    private void getRecordData(final int page){
        if(AccountManager.getInstance().isLogin()){
            UserVO userVO = AccountManager.getInstance().getUser();
            createHttp(UserService.class)
                    .getBetRecord(userVO.getAccount(), page, rows)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<ResultData>() {
                        @Override
                        public void onSuccess(ResultData value) {
                            if(value != null){
                                List<BetRecordModel> recordModels = value.getBetRecords();
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
                            toast(R.string.connection_failed);
                        }
                    });
        }
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
            case R.id.iv_refresh:
                page = 1;
                getRecordData(page);
                break;
        }

    }
}