package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.SelectLotteryListAdapter;
import com.forum.lottery.model.bet.BetBigBigModel;
import com.forum.lottery.model.bet.BetItemModel;
import com.forum.lottery.model.bet.BetListItemModel;
import com.forum.lottery.ui.BaseBetFragment;
import com.forum.lottery.utils.LotteryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin_h on 2017/5/23.
 */

public class LotterySelectFragment extends BaseBetFragment {

    private ListView lv_lottery;
    private static List<BetBigBigModel> dataAll;
    private List<BetListItemModel> data;
    private SelectLotteryListAdapter adapter;

    public static LotterySelectFragment newInstance(List<BetBigBigModel> dataAll){
        Bundle args = new Bundle();
        LotterySelectFragment.dataAll = dataAll;

        LotterySelectFragment fragment = new LotterySelectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lottery_select,container, false);
    }

    @Override
    public void clearCheckedBetting() {
        adapter.clearCheckedBetting();
    }

    @Override
    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        lv_lottery = findView(R.id.lv_lottery);
        lv_lottery.setAdapter(adapter);
    }

    @Override
    protected void initData() {

        if(data == null){
            data = new ArrayList<>();
        }
        if(adapter == null){
            adapter = new SelectLotteryListAdapter(getActivity(), data);
        }
    }

    public void setPlayId(String playId){
        if(data == null){
            data = LotteryUtils.getBetListItem(dataAll, playId);
        }else{
            data.clear();
            data.addAll(LotteryUtils.getBetListItem(dataAll, playId));
        }
        if(adapter == null){
            adapter = new SelectLotteryListAdapter(getActivity(), data);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    public List<BetListItemModel> getData(){
        return data;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
