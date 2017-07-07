package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.SelectLotteryListAdapter;
import com.forum.lottery.model.PlayTypeA;
import com.forum.lottery.model.PlayTypeB;
import com.forum.lottery.model.bet.BetBigAllModel;
import com.forum.lottery.model.bet.BetBigBigModel;
import com.forum.lottery.model.bet.BetListItemModel;
import com.forum.lottery.model.bet.BetListModel;
import com.forum.lottery.ui.BaseBetFragment;
import com.forum.lottery.utils.LotteryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2017/5/23.
 */

public class LotterySelectFragment extends BaseBetFragment {

    private View headView;
    private TextView tv_head;

    private ListView lv_lottery;
    private static BetBigAllModel dataAll;
//    private List<BetListItemModel> data;
    private BetListModel data = new BetListModel();
    private SelectLotteryListAdapter adapter;

    private static String lotteryId;

    private LinearLayout ll_betBottombar;

    public static LotterySelectFragment newInstance(BetBigAllModel dataAll, String lotteryid){
        Bundle args = new Bundle();
        LotterySelectFragment.dataAll = dataAll;
        LotterySelectFragment.lotteryId = lotteryid;

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
        adapter = new SelectLotteryListAdapter(getActivity(), data.getData(), lotteryId);
        lv_lottery = findView(R.id.lv_lottery);
        lv_lottery.setAdapter(adapter);
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_bet_layout, null);
        tv_head = (TextView) headView.findViewById(R.id.tv_head);
        if(lotteryId.equals("41") || lotteryId.equals("42") || lotteryId.equals("18")){
            lv_lottery.addHeaderView(headView);
        }

        ll_betBottombar = findView(R.id.ll_betBottombar);

        ((BuyLotteryActivity)getActivity()).initPopup();
    }

    @Override
    protected void initData() {

//        if(data == null){
//            data = new BetListModel();
//        }
//        if(adapter == null){
//            adapter = new SelectLotteryListAdapter(getActivity(), data.getData());
//        }
    }

    public void setPlayId(String playId, PlayTypeA typeA, PlayTypeB typeB){
//        if(data == null){
            data = LotteryUtils.getBetListItem(dataAll, playId, lotteryId, typeA, typeB);
//        }else{
//            data.clear();
//            data.addAll(LotteryUtils.getBetListItem(dataAll, playId, lotteryId));
//        }
//        if(adapter == null){
//            adapter = new SelectLotteryListAdapter(getActivity(), data.getData());
//        }else {
            adapter.setData(data.getData());
//        }

//        if(showBetBottomBar(typeA, typeB)){
        if(data.isSelPosition()){
            ll_betBottombar.setVisibility(View.VISIBLE);
        }else{
            ll_betBottombar.setVisibility(View.GONE);
        }

        tv_head.setText(typeB.getPlayTypeB());
    }

    public List<BetListItemModel> getData(){
        return data.getData();
    }

    private boolean showBetBottomBar(PlayTypeA typeA, PlayTypeB typeB){
        List<String> typeAs = Arrays.asList(getResources().getStringArray(R.array.bottombar_playA));
        List<String> typeBs = Arrays.asList(getResources().getStringArray(R.array.bottombar_playB));
        if(typeAs.contains(typeA) && typeBs.contains(typeB)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
