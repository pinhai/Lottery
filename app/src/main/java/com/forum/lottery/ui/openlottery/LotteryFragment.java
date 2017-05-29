package com.forum.lottery.ui.openlottery;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BuyListAdapter;
import com.forum.lottery.adapter.LotteryListAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.ui.TabBaseFragment;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/21.
 */

public class LotteryFragment extends TabBaseFragment {
    private ListView listLottery;
    private LotteryListAdapter adapter;
    private List<LotteryVO> lotteryVOs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lottery, container, false);
    }

    @Override
    public boolean initNet() {
        return true;
    }

    @Override
    public String getTitle() {
        return MyApplication.getInstance().getString(R.string.lottery_title);
    }

    @Override
    public int getIcon() {
        return R.drawable.nav_3_selector;
    }

    @Override
    protected void initView() {
        lotteryVOs = new ArrayList<>();
        adapter = new LotteryListAdapter(getActivity(), lotteryVOs);

        listLottery = findView(R.id.list_lottery);
        listLottery.setOnItemClickListener(onItemClickListener);
        listLottery.setAdapter(adapter);

        loadLotteryList();
    }

    @Override
    protected void initData() {

    }

    private void loadLotteryList(){
//        List<LotteryVO> lotteryVOs = new ArrayList<>();
//        for(int i = 0; i < 20; i++){
//            lotteryVOs.add(new LotteryVO());
//        }

        showIndeterminateDialog();
        createHttp(LotteryService.class)
                .getAllLotteryList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData<List<LotteryVO>>>() {
                    @Override
                    public void onSuccess(ResultData<List<LotteryVO>> value) {
                        dismissIndeterminateDialog();
                        if(value != null && value.getData() != null){
                            lotteryVOs.clear();
                            lotteryVOs.addAll(value.getData());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        dismissIndeterminateDialog();
                        toast(getString(R.string.connection_failed));
                    }
                });

//        listLottery.setAdapter(new LotteryListAdapter(getActivity(), lotteryVOs));
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LotteryListActivity.startActivity(getActivity(), lotteryVOs.get(i));
        }
    };

}
