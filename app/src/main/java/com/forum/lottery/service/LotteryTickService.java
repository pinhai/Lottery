package com.forum.lottery.service;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.forum.lottery.R;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.event.OpeningLotteryEvent;
import com.forum.lottery.event.RefreshLotteryListEvent;
import com.forum.lottery.event.RefreshLotteryListResultEvent;
import com.forum.lottery.model.NextIssueModel;
import com.forum.lottery.network.RxHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by admin on 2017/5/28.
 */

public class LotteryTickService extends Service {

    private static final String TAG = LotteryTickService.class.getSimpleName();
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

    @Subscribe
    public void openingLottery(OpeningLotteryEvent event){
        checkOpenStatusLotteryId = event.lotteryId;
        checkOpenStatusPlay = event.play;
        checkOpenStatus = true;
    }

    String checkOpenStatusLotteryId = "0", checkOpenStatusPlay = "0";
    boolean checkOpenStatus = false;
    int checkOpenStatusTick = 0;
    //查询下一期开奖结果
    private void checkOpenStatus() {
        Log.v(TAG, "开始查询下一期开奖结果" );
        createHttp(LotteryService.class)
                .getNextIssue(checkOpenStatusLotteryId, "3")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<NextIssueModel>() {
                    @Override
                    public void onSuccess(NextIssueModel value) {
                        List<String> code = value.getCode();
                        if(code != null && code.size() > 1){
                            loadLotteryList();
                            Log.v(TAG, "查询到下一期开奖结果  " + code.toString() );
                        }else {
                            checkOpenStatus = true;
                            checkOpenStatusTick = 0;
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        checkOpenStatus = true;
                        checkOpenStatusTick = 0;
                    }
                });
    }

    private boolean manualRefresh = false;  //如果为手动刷新，则刷新后发送RefreshLotteryListResultEvent事件
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
    private Timer timer;
    private int refreshInterval = 0;
    private void startTick(){
//        if(!tickThread.isAlive())
//            tickThread.start();

        if(timer == null){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (runTick){
                        try {
                            Log.d(TAG, "当前时间戳：" + System.currentTimeMillis());
//                        Thread.sleep(1000);
                            handler.sendEmptyMessage(0);
                            ++ refreshInterval;
                            if(refreshInterval > 5*60){
                                refreshInterval = 0;
                                loadLotteryList();
                            }

                            ++checkOpenStatusTick;
                            if(checkOpenStatus && checkOpenStatusTick > 3){
                                checkOpenStatus = false;
                                checkOpenStatus();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 1000, 1000);
        }
    }

    private void initTick(){

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
//
//                        ++checkOpenStatusTick;
//                        if(checkOpenStatus && checkOpenStatusTick > 3){
//                            checkOpenStatus = false;
//                            checkOpenStatus();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
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
