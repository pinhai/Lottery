package com.forum.lottery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;

import com.forum.lottery.adapter.MainTabAdapter;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.ui.buy.BuyFragment;
import com.forum.lottery.ui.home.HomeFragment;
import com.forum.lottery.ui.openlottery.LotteryFragment;
import com.forum.lottery.ui.own.OwnFragment;
import com.forum.lottery.ui.trend.TrendFragment;
import com.forum.lottery.utils.ToolUtils;
import com.forum.lottery.utils.Utils;
import com.forum.lottery.view.TabBar;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private MainTabAdapter mainTabAdapter;
    private TabBar mainTabBar;
    private Handler handler;
    private boolean isExit = false;
    private int curItem = 0;
    private static final int BASE_PERMISSION_REQUEST_CODE = 10;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context,String url) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.initDisplayMetrics(this);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        mainTabBar = findView(R.id.main_tab);
        mainTabBar.setPagerAdapter(mainTabAdapter, curItem);
    }


    @Override
    protected void initParams(Bundle bundle) {
        super.initParams(bundle);
        curItem = bundle.getInt("curItem", 0);
    }

    @Override
    protected void initData() {
        mainTabAdapter = new MainTabAdapter(getSupportFragmentManager(), generateTabFragments());
        handler = new Handler();

        requestBasePermission();
    }


    private void requestBasePermission(){
        if(ToolUtils.isUpLollipop()){
            requestPermissions(BASE_PERMISSION_REQUEST_CODE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})
                    .subscribe(new SingleSubscriber<String[]>() {
                        @Override
                        public void onSuccess(String[] value) {
                            if(value.length > 0)
                                toast("需要权限被拒绝，会影响部分功能的正常使用。");
                        }
                        @Override
                        public void onError(Throwable error) {}
                    });
        }
    }

    private List<TabBaseFragment> generateTabFragments() {
        List<TabBaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new BuyFragment());
        fragmentList.add(new LotteryFragment());
        fragmentList.add(new TrendFragment());
        fragmentList.add(new OwnFragment());
        return fragmentList;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isExit) {
                    handler.removeCallbacks(exitRunnable);
                    Schedulers.shutdown();
                    System.exit(0);
                } else {
                    handler.postDelayed(exitRunnable, 2000);
                    isExit = true;
                    toast("再按一次退出" + getString(R.string.app_name));
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private Runnable exitRunnable = new Runnable() {
        @Override
        public void run() {
            isExit = false;
        }
    };

}

