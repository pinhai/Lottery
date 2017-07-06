package com.forum.lottery.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.forum.lottery.R;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.event.RefreshLotteryListEvent;
import com.forum.lottery.event.RefreshLotteryListResultEvent;
import com.forum.lottery.network.RxHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by admin_h on 2017/5/28.
 */

public class LotteryTickService extends Service {

    private RxHttp rxHttp;

    private List<LotteryVO> lotteryVOs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);

        lotteryVOs = new ArrayList<>();
        initTick();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        loadLotteryList();
        return super.onStartCommand(intent, flags, startId);
    }

    private boolean manualRefresh = false;
    @Subscribe
    public void refreshLotteryList(RefreshLotteryListEvent event){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                manualRefresh = true;
                loadLotteryList();
            }
        }, event.mills);

    }


    private void loadLotteryList(){

        createHttp(LotteryService.class)
                .getAllLotteryList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData<List<LotteryVO>>>() {
                    @Override
                    public void onSuccess(ResultData<List<LotteryVO>> value) {
                        if(value != null && value.getData() != null){
                            lotteryVOs.clear();
                            lotteryVOs.addAll(value.getData());
//                            adapter.notifyDataSetChanged();
                            refreshInterval = 0;
                            startTick();
                        }
                        if(manualRefresh){
                            manualRefresh = false;
                            EventBus.getDefault().post(new RefreshLotteryListResultEvent(lotteryVOs));
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                        if(manualRefresh){
                            manualRefresh = false;
                            EventBus.getDefault().post(new RefreshLotteryListResultEvent(lotteryVOs));
                        }
                    }
                });

    }

    private android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            for(LotteryVO lotteryVO : lotteryVOs){
                int time = lotteryVO.getTime() - 1;
                lotteryVO.setTime(time);
            }
            EventBus.getDefault().post(new LotteryListTickEvent(lotteryVOs));
            return false;
        }
    });

    private boolean runTick = true;
    private Thread tickThread;
    private int refreshInterval = 0;
    private void startTick(){
        if(!tickThread.isAlive())
            tickThread.start();
    }

    private void initTick(){
        tickThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (runTick){
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(0);
                        ++ refreshInterval;
                        if(refreshInterval > 5*60){
                            refreshInterval = 0;
                            loadLotteryList();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void toast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建网络service
     * @param serviceName
     * @param <T>
     * @return
     */
    public <T> T createHttp(Class<T> serviceName){
        if(rxHttp == null)
            rxHttp = new RxHttp();
        return rxHttp.create(serviceName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
