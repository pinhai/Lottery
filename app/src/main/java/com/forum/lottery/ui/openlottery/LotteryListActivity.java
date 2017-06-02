package com.forum.lottery.ui.openlottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.LotteryListAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.model.TrendModel;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/30.
 */

public class LotteryListActivity extends BaseActivity {
    private ImageView img_back;
    private ImageView iv_refresh;

    private ListView listLottery;
    private List<LotteryVO> lotteryVOs;
    private LotteryListAdapter lotteryListAdapter;
    private int type;
//    private String title;
    private LotteryVO lotteryVO;

    private View loadMore;
    private TextView tv_loadMore;

    private int start = 0;

    public static void startActivity(Context context, LotteryVO lotteryVO){
        Intent intent = new Intent(context, LotteryListActivity.class);
//        intent.putExtra("title", "重庆时时彩");
        intent.putExtra("type", 1);
        intent.putExtra("lottery", lotteryVO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_list);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        img_back = findView(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_refresh = findView(R.id.iv_refresh);
        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = 0;
                loadLotteryList();
            }
        });

        loadMore = LayoutInflater.from(this).inflate(R.layout.view_load_more, null);
        tv_loadMore = (TextView) loadMore.findViewById(R.id.tv_loadMore);
        tv_loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_loadMore.setText(R.string.loading);
                loadLotteryList();
            }
        });

        listLottery = findView(R.id.list_lottery);
        listLottery.addFooterView(loadMore);
        lotteryVOs = new ArrayList<>();
        lotteryListAdapter = new LotteryListAdapter(this, lotteryVOs);
        lotteryListAdapter.setHideTitle(true);
        lotteryListAdapter.setViewType(type);
        listLottery.setAdapter(lotteryListAdapter);
        loadLotteryList();
    }

    @Override
    protected void initData() {
        setTitle(lotteryVO.getLotteryName());
    }

    @Override
    protected void initParams(Bundle bundle) {
        super.initParams(bundle);
//        title = bundle.getString("title");
        type = bundle.getInt("type", 0);
        lotteryVO = (LotteryVO) bundle.getSerializable("lottery");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",lotteryVO.getLotteryName());
        outState.putInt("type", type);
    }

    private void loadLotteryList(){
//        for(int i=0; i<20; i++){
//            lotteryVOs.add(new LotteryVO());
//        }
//        lotteryListAdapter.notifyDataSetChanged();

        showIndeterminateDialog();
        createHttp(LotteryService.class)
                .getTrendData(lotteryVO.getLotteryid(), 20, start+"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        dismissIndeterminateDialog();
                        if(value != null && value.getRecords() != null){
                            if(value.getRecords().size() == 0){
                                tv_loadMore.setText(R.string.load_all);
                            }else{
                                tv_loadMore.setText(R.string.load_more);
                                if(start == 0){
                                    lotteryVOs.clear();
                                    toast(R.string.refresh_successful);
                                }
                            }
                            start += value.getRecords().size();
                            lotteryVOs.addAll(trend2Lotterys(value.getRecords()));
                            lotteryListAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        dismissIndeterminateDialog();
                        toast(getString(R.string.connection_failed));
                    }
                });


    }

    private List<LotteryVO> trend2Lotterys(List<TrendModel> trendModels){
        List<LotteryVO> lotteryVOs = new ArrayList<>();
        for(TrendModel item : trendModels){
            LotteryVO temp = new LotteryVO();
            temp.setLotteryName(lotteryVO.getLotteryName());
            temp.setLotteryid(lotteryVO.getLotteryid());
            temp.setOpenNum(item.getAllcode());
            temp.setOpenTime(item.getOpentime());
            temp.setIssue(item.getIssue());
            lotteryVOs.add(temp);
        }
        return lotteryVOs;
    }

}
