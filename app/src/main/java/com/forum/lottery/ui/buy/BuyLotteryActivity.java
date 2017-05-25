package com.forum.lottery.ui.buy;

import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.event.BuyLotteryCheckChangeEvent;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.model.BetItemModel;
import com.forum.lottery.model.BetListItemModel;
import com.forum.lottery.model.PlayTypeA;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.ui.BaseBetFragment;
import com.forum.lottery.utils.LotteryUtils;
import com.forum.lottery.view.PlayWaySelectorPopup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 买彩票界面
 * Created by Administrator on 2017/5/2.
 */

public class BuyLotteryActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_betCount, tv_betMoney, tv_tickTime, tv_playWaySelect;
    private Button btn_clear, btn_bet;
    private AlertDialog betDialog;
    private int betCount = 0;      //下注的注数
    private LotteryVO lotteryVO;

    private BaseBetFragment betFragment;

    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;
    private static final int UPTATE_INTERVAL_TIME = 50;
    private static final int SPEED_SHRESHOLD = 100;//这个值调节灵敏度
    private long lastUpdateTime;
    private float lastX;
    private float lastY;
    private float lastZ;

    private List<BetListItemModel> data;    //当前玩法对应的所有item和check状态
    private List<BetDetailModel> betDetailModels;  //下的注
    private List<PlayTypeA> playWays;  //当前彩票下所有玩法

    private PlayWaySelectorPopup playWaySelectorPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_lottery);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (sensor != null) {
            sensorManager.registerListener(sensorEventListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_GAME);//这里选择感应频率
        }
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        betFragment = DingWeiDanSelectFragment.newInstance(onCheckedListener);
        betFragment = LotterySelectFragment.newInstance(data);
        ft.add(R.id.rl_container, betFragment);
        ft.commit();

        tv_betCount = findView(R.id.tv_betCount);
        tv_betMoney = findView(R.id.tv_betMoney);
        btn_bet = findView(R.id.btn_bet);
        btn_bet.setOnClickListener(this);
        btn_clear = findView(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        tv_tickTime = findView(R.id.tv_tickTime);
        tv_playWaySelect = findView(R.id.tv_playWaySelect);
        tv_playWaySelect.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        betDetailModels = new ArrayList<>();
        lotteryVO = (LotteryVO) getIntent().getSerializableExtra("lottery");
        playWays = LotteryUtils.getPlayType(this, lotteryVO.getLotteryid());
        playWaySelectorPopup = new PlayWaySelectorPopup(this, playWays);
        loadBetData();
        initTick();
        startTick();

    }

    private void clearBet(){
        tv_betCount.setText(String.valueOf(0));
        tv_betMoney.setText(String.valueOf(0));
        betFragment.clearCheckedBetting();
    }

    private void loadBetData() {
        data = new ArrayList<>();
        List<BetItemModel> itemIntenal1 = new ArrayList<>();
        List<BetItemModel> itemIntenal2 = new ArrayList<>();
        List<BetItemModel> itemIntenal3 = new ArrayList<>();
        List<BetItemModel> itemIntenal4 = new ArrayList<>();
        List<BetItemModel> itemIntenal5 = new ArrayList<>();
        for(int i=0; i<10; i++){
            itemIntenal1.add(new BetItemModel(i+"", false));
            itemIntenal2.add(new BetItemModel(i+"", false));
            itemIntenal3.add(new BetItemModel(i+"", false));
            itemIntenal4.add(new BetItemModel(i+"", false));
            itemIntenal5.add(new BetItemModel(i+"", false));
        }

        BetListItemModel listitem1 = new BetListItemModel();
        listitem1.setLabel("万位");
        listitem1.setBetItems(itemIntenal1);
        BetListItemModel listitem2 = new BetListItemModel();
        listitem2.setLabel("千位");
        listitem2.setBetItems(itemIntenal2);
        BetListItemModel listitem3 = new BetListItemModel();
        listitem3.setLabel("百位");
        listitem3.setBetItems(itemIntenal3);
        BetListItemModel listitem4 = new BetListItemModel();
        listitem4.setLabel("十位");
        listitem4.setBetItems(itemIntenal4);
        BetListItemModel listitem5 = new BetListItemModel();
        listitem5.setLabel("个位");
        listitem5.setBetItems(itemIntenal5);

        data.add(listitem1);
        data.add(listitem2);
        data.add(listitem3);
        data.add(listitem4);
        data.add(listitem5);


    }

    /**
     * 下注
     */
    private void bet() {
        if(betCount <= 0){
            toast("请先下注！");
            return;
        }
//        LotteryUtils.getBetLotteryFromAddition(data);
        showBetDialog();
    }

    private void showBetDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_bet,null);
        betDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        final EditText et_oneBetMoney = (EditText) view.findViewById(R.id.et_oneBetMoney);
        TextView tv_betCount = (TextView) view.findViewById(R.id.tv_betCount);
        tv_betCount.setText(betCount + "");
        final TextView tv_betAllMoney = (TextView) view.findViewById(R.id.tv_betAllMoney);
        final TextView tv_peilv = (TextView) view.findViewById(R.id.tv_peilv);
        final TextView tv_oneHigh = (TextView) view.findViewById(R.id.tv_oneHigh);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                betDialog.dismiss();
            }
        });
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String one = et_oneBetMoney.getText().toString().trim();
                if(TextUtils.isEmpty(one)){
                    toast("请输入单注金额");
                    return;
                }
                float oneBetMoney = Float.parseFloat(one);
                float peilv = Float.parseFloat(tv_peilv.getText().toString());
                float fanli = 0;
                //去下注
                Intent i = new Intent(BuyLotteryActivity.this, BuyLotteryFinalActivity.class);
                betDetailModels = LotteryUtils.getBettedLottery(data, lotteryVO, betCount, oneBetMoney, peilv, fanli);
                i.putExtra("betDetails", (Serializable) betDetailModels);
                startActivity(i);

            }
        });
        et_oneBetMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    float oneBetMoney = Float.parseFloat(s.toString());
                    tv_betAllMoney.setText((oneBetMoney*betCount) + " 元");
                    float peilv = Float.parseFloat(tv_peilv.getText().toString());
                    tv_oneHigh.setText((oneBetMoney*peilv) + " 元");
                }else {
                    tv_betAllMoney.setText("0");
                    tv_oneHigh.setText("0");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        betDialog.show();
    }

    /**
     * 机选
     */
    private void selectByMachine(){
        LotteryUtils.selectByMachineFromAddition(data);
        betFragment.notifyDataSetChanged();
        selectLotteryBetEvent(new BuyLotteryCheckChangeEvent());
    }

    @Subscribe
    public void selectLotteryBetEvent(BuyLotteryCheckChangeEvent event){
        betCount = LotteryUtils.getBetCountFromAddition(data);
        tv_betCount.setText(betCount + "");
        tv_betMoney.setText((betCount *2) + "");
    }

    private android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int time = lotteryVO.getTime() - 1;
            lotteryVO.setTime(time);
            tv_tickTime.setText(LotteryUtils.secToTime(time));
            return false;
        }
    });

    private boolean runTick = true;
    private Thread tickThread;
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                clearBet();
                break;
            case R.id.btn_bet:
                bet();
                break;
            case R.id.tv_playWaySelect:
                if(playWaySelectorPopup.isShowing()){
                    playWaySelectorPopup.dismiss();
                }else{
                    playWaySelectorPopup.show(tv_playWaySelect);
                }
                break;
        }
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event){
            long currentUpdateTime = System.currentTimeMillis();
            long timeInterval = currentUpdateTime - lastUpdateTime;
            if(timeInterval < UPTATE_INTERVAL_TIME){
                return;
            }
            lastUpdateTime = currentUpdateTime;
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;


            lastX = x;
            lastY = y;
            lastZ = z;
            double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY
                    + deltaZ * deltaZ) / timeInterval) * 100;
            if(speed >= SPEED_SHRESHOLD){
                //摇一摇、
                vibrator.vibrate(100);
                selectByMachine();
//                image.setImageResource(R.drawable.running01);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy){

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
