package com.forum.lottery.ui.openlottery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.LotteryListAdapter;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.ui.TabBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class LotteryFragment extends TabBaseFragment {
    private ListView listLottery;
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
        listLottery = findView(R.id.list_lottery);
        listLottery.setOnItemClickListener(onItemClickListener);
        loadLotteryList();
    }

    @Override
    protected void initData() {

    }

    private void loadLotteryList(){
        List<LotteryVO> lotteryVOs = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            lotteryVOs.add(new LotteryVO());
        }
        listLottery.setAdapter(new LotteryListAdapter(getActivity(), lotteryVOs));
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LotteryListActivity.startActivity(getActivity(), null);
        }
    };
}
