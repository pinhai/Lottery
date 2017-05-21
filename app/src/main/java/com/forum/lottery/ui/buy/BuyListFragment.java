package com.forum.lottery.ui.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BuyListAdapter;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/1.
 */

public class BuyListFragment extends BaseFragment {
    private ListView listBuy;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_list,container, false);
    }

    @Override
    protected void initView() {
        listBuy = findView(R.id.list_buy);
        loadLotteryList();
        listBuy.setOnItemClickListener(new BuyItemClickListener());
    }

    @Override
    protected void initData() {

    }

    private void loadLotteryList(){
        List<LotteryVO> lotteryVOs = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            lotteryVOs.add(new LotteryVO());
        }
        listBuy.setAdapter(new BuyListAdapter(getActivity(), lotteryVOs));
    }

    private class BuyItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), BuyLotteryActivity.class);
            startActivity(intent);
        }
    }
}
