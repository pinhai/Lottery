package com.forum.lottery.ui.buy;

import android.content.Intent;
import android.opengl.ETC1;
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
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.ui.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/5/1.
 */

public class BuyListFragment extends BaseFragment {
    private ListView listBuy;
    private BuyListAdapter adapter;
    private List<LotteryVO> lotteryVOs;

    private int currentItem;

    public static BuyListFragment newInstant(int radioId) {
        Bundle args = new Bundle();
        args.putInt("currentItem", radioId);

        BuyListFragment fragment = new BuyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_list,container, false);
    }

    @Override
    protected void initView() {
        listBuy = findView(R.id.list_buy);
//        loadLotteryList();
        listBuy.setOnItemClickListener(new BuyItemClickListener());
        listBuy.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        lotteryVOs = new ArrayList<>();
        currentItem = getArguments().getInt("currentItem");
        EventBus.getDefault().register(this);
//        initTick();
//        for(int i = 0; i < 20; i++){
//            lotteryVOs.add(new LotteryVO());
//        }
        adapter = new BuyListAdapter(getActivity(), lotteryVOs);
    }

    @Subscribe
    public void deliveryLotteryList(LotteryListTickEvent event){
        lotteryVOs.clear();
        lotteryVOs.addAll(event.data);
//        lotteryVOs = event.data;
        List<String> dipincai = Arrays.asList(getResources().getStringArray(R.array.dipincai));
        if(currentItem == R.id.radio_high){
            //高频彩
            for(int i=0; i<lotteryVOs.size(); i++){
                LotteryVO lotteryVO = lotteryVOs.get(i);
                for (String dpc : dipincai){
                    if(dpc.equals(lotteryVO.getLotteryid())){
                        lotteryVOs.remove(i);
                        i--;
                    }
                }
            }
        }else if(currentItem == R.id.radio_low){
            //低频彩
            for(int i=0; i<lotteryVOs.size(); i++){
                LotteryVO lotteryVO = lotteryVOs.get(i);
                boolean flag = false;
                for (String dpc : dipincai){
                    if(dpc.equals(lotteryVO.getLotteryid())){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    lotteryVOs.remove(i);
                    i--;
                }
            }
        }
        adapter.setData(lotteryVOs);
        adapter.notifyDataSetChanged();
    }

//    private void loadLotteryList(){
//
//        createHttp(LotteryService.class)
//                .getAllLotteryList()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleSubscriber<ResultData<List<LotteryVO>>>() {
//                    @Override
//                    public void onSuccess(ResultData<List<LotteryVO>> value) {
//                        if(value != null && value.getData() != null){
//                            lotteryVOs.clear();
//                            lotteryVOs.addAll(value.getData());
//                            adapter.notifyDataSetChanged();
//                            startTick();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable error) {
//                        toast(getString(R.string.connection_failed));
//                    }
//                });
//
//    }

//    private android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            for(LotteryVO lotteryVO : lotteryVOs){
//                int time = lotteryVO.getTime() - 1;
//                lotteryVO.setTime(time);
//                if(time <= 0){
//
////                    loadLotteryList();
//                }
//            }
//            adapter.notifyDataSetChanged();
//            return false;
//        }
//    });

    private boolean runTick = true;
    private Thread tickThread;
    private int refreshInterval = 0;
//    private void startTick(){
//        if(!tickThread.isAlive())
//            tickThread.start();
//    }

//    private void initTick(){
//        tickThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (runTick){
//                    try {
//                        Thread.sleep(1000);
//                        handler.sendEmptyMessage(0);
//                        ++ refreshInterval;
//                        if(refreshInterval > 5*60){
//                            refreshInterval = 0;
//                            loadLotteryList();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

    private class BuyItemClickListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), BuyLotteryActivity.class);
            intent.putExtra("lottery", lotteryVOs.get(position));
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
