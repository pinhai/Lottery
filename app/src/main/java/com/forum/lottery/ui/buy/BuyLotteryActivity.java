package com.forum.lottery.ui.buy;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.event.BuyLotteryCheckChangeEvent;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.event.RefreshLotteryListEvent;
import com.forum.lottery.model.BetDetailModel;
import com.forum.lottery.model.bet.BetBigAllModel;
import com.forum.lottery.model.bet.BetBigBigModel;
import com.forum.lottery.model.Peilv;
import com.forum.lottery.model.PlayTypeA;
import com.forum.lottery.model.PlayTypeB;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.ui.openlottery.LotteryListActivity;
import com.forum.lottery.ui.own.BetRecordActivity;
import com.forum.lottery.ui.trend.TrendActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.utils.LotteryUtils;
import com.forum.lottery.utils.ScreenUtils;
import com.forum.lottery.view.PlayWaySelectorPopup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 买彩票界面
 * Created by Administrator on 2017/5/2.
 */

public class BuyLotteryActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_assistant;
    private TextView tv_betCount, tv_betMoney, tv_playWaySelect;
    private TextView tv_issue, tv_nextIssue, tv_openNum, tv_tickTime;
    private Button btn_clear, btn_bet;
    private AlertDialog betDialog;
    private int betCount = 0;      //下注的注数
    private LotteryVO lotteryVO;
    private boolean backAddBet;  //是否从“返回添加一注”进来

    private LotterySelectFragment betFragment;

    //机选
    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;
    private static final int UPTATE_INTERVAL_TIME = 50;
    private static final int SPEED_SHRESHOLD = 70;//这个值调节灵敏度
    private long lastUpdateTime;
    private float lastX;
    private float lastY;
    private float lastZ;

    private BetBigAllModel dataAll;    //当前玩法对应的所有item和check状态
    private List<BetDetailModel> betDetailModels;  //下的注
    private List<PlayTypeA> playWays;  //当前彩票下所有玩法

    private PlayWaySelectorPopup playWaySelectorPopup;
    private PlayTypeA playTypeA;  //当前选中的玩法a
    private PlayTypeB playTypeB;  //当前选中的玩法b

    private PopupWindow pw_assistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_lottery);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        initData();
        initView();
//        initPopup();
        if(!AccountManager.getInstance().isLogin()){
            toast("当前未登录，无法购彩！");
        }
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
        findView(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EventBus.getDefault().register(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        betFragment = DingWeiDanSelectFragment.newInstance(onCheckedListener);
        betFragment = LotterySelectFragment.newInstance(dataAll, lotteryVO.getLotteryid());
        ft.add(R.id.rl_container, betFragment);
        ft.commitNow();

        iv_assistant = findView(R.id.iv_assistant);
        iv_assistant.setOnClickListener(this);
        tv_betCount = findView(R.id.tv_betCount);
        tv_betMoney = findView(R.id.tv_betMoney);
        btn_bet = findView(R.id.btn_bet);
        btn_bet.setOnClickListener(this);
        btn_clear = findView(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        tv_tickTime = findView(R.id.tv_tickTime);
        tv_issue = findView(R.id.tv_issue);
        tv_nextIssue = findView(R.id.tv_nextIssue);
        tv_openNum = findView(R.id.tv_openNum);
        tv_playWaySelect = findView(R.id.tv_playWaySelect);
        tv_playWaySelect.setOnClickListener(this);

        refreshIssue();

        initAssistantPopup();
    }

    private void initAssistantPopup() {

        View view = LayoutInflater.from(this).inflate(R.layout.view_assistant, null);
        view.findViewById(R.id.tv_betRecord).setOnClickListener(this);
        view.findViewById(R.id.tv_trend).setOnClickListener(this);
        view.findViewById(R.id.tv_recentOpen).setOnClickListener(this);
//        view.findViewById(R.id.tv_playWayPrompt).setOnClickListener(this);
        // 创建PopupWindow对象
        pw_assistant = new PopupWindow(view, ScreenUtils.dp2px(130), android.view.ViewGroup.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pw_assistant.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pw_assistant.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pw_assistant.setFocusable(true);
//        pw_assistant.getContentView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                return false;
//            }
//        });
    }

    @Override
    protected void initData() {
        betDetailModels = new ArrayList<>();
        lotteryVO = (LotteryVO) getIntent().getSerializableExtra("lottery");
        backAddBet = getIntent().getBooleanExtra("backAddBet", false);
        playWays = new ArrayList<>();
//        playWays = LotteryUtils.getPlayType(this, lotteryVO.getLotteryid());
        loadBetData();

    }

    private void refreshIssue(){
        String openNum = "";
        for(String s : lotteryVO.getOpenNum()){
            openNum += " " + s;
        }
        tv_issue.setText(lotteryVO.getIssue() + "期");
        tv_openNum.setText(openNum);
        tv_nextIssue.setText("距" + lotteryVO.getNextIssue() + "期截止");
    }

    @Subscribe
    public void deliveryLotteryList(LotteryListTickEvent event){
        if(!runTick){
            List<LotteryVO> lotteryVOs = event.data;
            for(LotteryVO item : lotteryVOs){
                if(item.getLotteryid().equals(lotteryVO.getLotteryid())){
                    lotteryVO = item;
                }
            }
            if(lotteryVO.getTime() > 0){
                runTick = true;
                initTick();
                startTick();
            }
        }

//        tv_tickTime.setText(LotteryUtils.secToTime(lotteryVO.getTime()));

    }

    public void initPopup() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playWaySelectorPopup = new PlayWaySelectorPopup(BuyLotteryActivity.this, playWays, playTypeCheckListener);
            }
        }, 0);
        initTick();
        startTick();
    }

    private void clearBet(){
        tv_betCount.setText(String.valueOf(0));
        tv_betMoney.setText(String.valueOf(0));
        betFragment.clearCheckedBetting();
    }

    private void loadBetData() {
//        dataAll = new ArrayList<>();
//        List<BetItemModel> itemIntenal1 = new ArrayList<>();
//        List<BetItemModel> itemIntenal2 = new ArrayList<>();
//        List<BetItemModel> itemIntenal3 = new ArrayList<>();
//        List<BetItemModel> itemIntenal4 = new ArrayList<>();
//        List<BetItemModel> itemIntenal5 = new ArrayList<>();
//        for(int i=0; i<10; i++){
//            itemIntenal1.add(new BetItemModel(i+"", false));
//            itemIntenal2.add(new BetItemModel(i+"", false));
//            itemIntenal3.add(new BetItemModel(i+"", false));
//            itemIntenal4.add(new BetItemModel(i+"", false));
//            itemIntenal5.add(new BetItemModel(i+"", false));
//        }
//
//        BetListItemModel listitem1 = new BetListItemModel();
//        listitem1.setTitle("万位");
//        listitem1.setBetItems(itemIntenal1);
//        BetListItemModel listitem2 = new BetListItemModel();
//        listitem2.setTitle("千位");
//        listitem2.setBetItems(itemIntenal2);
//        BetListItemModel listitem3 = new BetListItemModel();
//        listitem3.setTitle("百位");
//        listitem3.setBetItems(itemIntenal3);
//        BetListItemModel listitem4 = new BetListItemModel();
//        listitem4.setTitle("十位");
//        listitem4.setBetItems(itemIntenal4);
//        BetListItemModel listitem5 = new BetListItemModel();
//        listitem5.setTitle("个位");
//        listitem5.setBetItems(itemIntenal5);
//
//        dataAll.add(listitem1);
//        dataAll.add(listitem2);
//        dataAll.add(listitem3);
//        dataAll.add(listitem4);
//        dataAll.add(listitem5);


        dataAll = LotteryUtils.getBetLayout(this, lotteryVO.getLotteryid(), playWays);


    }

    /**
     * 下注
     */
    private void bet() {
        if(!AccountManager.getInstance().isLogin()){
            toast("当前未登录，无法购彩！");
            return;
        }
        if(betCount <= 0){
            toast("请先下注！");
            return;
        }

        showProgressDialog(false);
        //验证合理性
//        String buyNo = URLEncoder.encode(LotteryUtils.getBetLottery(dataAll));
        String buyNo = LotteryUtils.encode(LotteryUtils.getBetLottery(betFragment.getData()));

        createHttp(LotteryService.class)
                .lotteryNumsCheck(buyNo, lotteryVO.getLotteryid(), playTypeB.getPlayId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        if(value.getCode() == 1){
                            //获取赔率
                            betGetPeilv();
                        }else{
                            toast(getString(R.string.bet_num_not_conform));
                            dismissProgressDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                        dismissProgressDialog();
                    }
                });

//        LotteryUtils.getBetLotteryFromAddition(dataAll);
    }

    private void betGetPeilv() {

        createHttp(LotteryService.class)
                .getPeilv(lotteryVO.getLotteryid(), playTypeB.getPlayId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<Peilv>>() {
                    @Override
                    public void onSuccess(List<Peilv> value) {
                        dismissProgressDialog();
                        if(value != null && value.get(0) != null){
                            Peilv peilv = value.get(0);
                            showBetDialog(peilv);
                        }else {
                            toast(getString(R.string.connection_failed));
                        }

                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                        dismissProgressDialog();
                    }
                });

    }

    private void showBetDialog(Peilv peilv) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_bet,null);
        betDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        final EditText et_oneBetMoney = (EditText) view.findViewById(R.id.et_oneBetMoney);
        TextView tv_betCount = (TextView) view.findViewById(R.id.tv_betCount);
        tv_betCount.setText(betCount + "");
        final TextView tv_betAllMoney = (TextView) view.findViewById(R.id.tv_betAllMoney);
        final TextView tv_peilv = (TextView) view.findViewById(R.id.tv_peilv);
        if(peilv != null){
            tv_peilv.setText(peilv.getBonusProp() + "");
        }else {
            tv_peilv.setText(playTypeB.getPeilv());
        }
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
                String playName = "[" + playTypeA.getPlayTypeA() + "_" + playTypeB.getPlayTypeB() + "]";
                betDetailModels = LotteryUtils.getBettedLottery(betFragment.getData(), lotteryVO, betCount, oneBetMoney, peilv, fanli,
                        playTypeB.getPlayId(), playName);
                if(backAddBet){
                    Intent i2 = new Intent();
                    i2.putExtra("backBetData", (Serializable) betDetailModels);
                    setResult(201, i2);
                    finish();
                }else{
                    Intent i = new Intent(BuyLotteryActivity.this, BuyLotteryFinalActivity.class);
                    i.putExtra("betDetails", (Serializable) betDetailModels);
                    i.putExtra("lottery", lotteryVO);
                    startActivityForResult(i, 101);
                }

                betDialog.dismiss();

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
        //自己生成
//        LotteryUtils.selectByMachineFromAddition(dataAll);
//        betFragment.notifyDataSetChanged();
//        selectLotteryBetEvent(new BuyLotteryCheckChangeEvent());
        //从后台获取
        createHttp(LotteryService.class)
                .getBetByMachine(lotteryVO.getLotteryid(), playTypeB.getPlayId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        if(value != null && value.getCode() == 1){
                            String result = value.getResult().trim();
                            LotteryUtils.selectByRemote(betFragment.getData(), result);
                            betFragment.notifyDataSetChanged();
                            selectLotteryBetEvent(new BuyLotteryCheckChangeEvent());
                        }else{
                            String show = "生成失败";
                            toast(show);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                    }
                });

    }

    @Subscribe
    public void selectLotteryBetEvent(BuyLotteryCheckChangeEvent event){
        betCount = LotteryUtils.getBetCountFromAddition(betFragment.getData());
        tv_betCount.setText(betCount + "");
        tv_betMoney.setText((betCount *2) + "");
        //// TODO: 2017/6/9 通过网络接口获取下注数量

    }

    private android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int time = lotteryVO.getTime() - 1;
            lotteryVO.setTime(time);
            if(time <= 0){
                runTick = false;
                EventBus.getDefault().post(new RefreshLotteryListEvent(10*1000));
                tv_tickTime.setText("正在开奖");
                tv_tickTime.setBackgroundResource(R.color.transparent);
            }else{
                tv_tickTime.setText(LotteryUtils.secToTime(time));
                tv_tickTime.setBackgroundResource(R.mipmap.shijian_bg);
            }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            //下注成功
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_assistant:
                pw_assistant.showAsDropDown(iv_assistant, -180, 0);
                break;
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
            case R.id.tv_betRecord:
                Intent intent = new Intent(BuyLotteryActivity.this, BetRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_trend:
                Intent intent2 = new Intent(BuyLotteryActivity.this, TrendActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_recentOpen:
                Intent intent3 = new Intent(BuyLotteryActivity.this, LotteryListActivity.class);
                intent3.putExtra("type", 1);
                intent3.putExtra("lottery", lotteryVO);
                startActivity(intent3);
                break;
//            case R.id.tv_playWayPrompt:
//
//                break;
        }
    }

    private PlayWaySelectorPopup.OnPlayTypeCheckListener playTypeCheckListener = new PlayWaySelectorPopup.OnPlayTypeCheckListener() {
        @Override
        public void playTypeChecked(PlayTypeA typeA, PlayTypeB typeB, String playId){
            playTypeA = typeA;
            playTypeB = typeB;
            tv_playWaySelect.setText(playTypeA.getPlayTypeA() + "-" + playTypeB.getPlayTypeB());
            //  刷新下注界面
            betFragment.setPlayId(playId, typeA, typeB);
        }
    };


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
