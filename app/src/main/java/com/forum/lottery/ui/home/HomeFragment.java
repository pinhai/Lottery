package com.forum.lottery.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.forum.lottery.MainActivity;
import com.forum.lottery.R;
import com.forum.lottery.adapter.LotteryGridAdapter;
import com.forum.lottery.adapter.WinningListAdapter;
import com.forum.lottery.api.LotteryService;
import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.LotteryVO;
import com.forum.lottery.event.LotteryListTickEvent;
import com.forum.lottery.model.WinnerModel;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.ui.buy.BuyLotteryActivity;
import com.forum.lottery.ui.login.LoginActivity;
import com.forum.lottery.ui.login.RegisterActivity;
import com.forum.lottery.ui.own.BetRecordActivity;
import com.forum.lottery.view.MyGridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/21.
 */

public class HomeFragment extends TabBaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    public static final int LOGIN_CODE = 100;
    public static final int REGISTER_CODE = 101;

    private ViewPager viewPager;
    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    /**
     * 图片资源id
     */
    private int[] imgIdArray ;

    private ListView lv_winning;
    private WinningListAdapter winningListAdapter;
    private List<WinnerModel> winnerData;

    private MyGridView gv_lottery;
    private LotteryGridAdapter lotteryGridAdapter;
    private List<LotteryVO> lotteryVOs;

    private TextView tv_cqmoney, tv_betRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public boolean initNet() {
        return true;
    }

    @Override
    public String getTitle() {
        return MyApplication.getInstance().getString(R.string.home_title);
    }

    @Override
    public int getIcon() {
        return R.drawable.nav_1_selector;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cqmoney:
                ((MainActivity)getActivity()).setCurrentPageItem(4);
                break;
            case R.id.tv_betRecord:
                Intent intent = new Intent(getActivity(), BetRecordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        tv_cqmoney = findView(R.id.tv_cqmoney);
        tv_cqmoney.setOnClickListener(this);
        tv_betRecord = findView(R.id.tv_betRecord);
        tv_betRecord.setOnClickListener(this);


        winnerData = new ArrayList<>();
        winningListAdapter = new WinningListAdapter(getActivity(), winnerData);
        loadWinnerData();
        lv_winning = findView(R.id.lv_winning);
        lv_winning.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        findView(R.id.txt_login).setOnClickListener(onClickListener);
        findView(R.id.txt_register).setOnClickListener(onClickListener);

        gv_lottery = findView(R.id.gv_lottery);
        lotteryVOs = new ArrayList<>();
        lotteryGridAdapter = new LotteryGridAdapter(getActivity(), lotteryVOs);
        gv_lottery.setAdapter(lotteryGridAdapter);
        gv_lottery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 9){
                    ((MainActivity)getActivity()).setCurrentPageItem(1);
                }else{
                    Intent intent = new Intent(getActivity(), BuyLotteryActivity.class);
                    intent.putExtra("lottery", lotteryVOs.get(position));
                    startActivity(intent);
                }

            }
        });

        initTick();
        startTick();

        viewPager = findView(R.id.viewPager);
        //载入图片资源ID
        imgIdArray = new int[]{R.mipmap.fous_01, R.mipmap.fous_02, R.mipmap.fous_01 };
        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        viewPager.setCurrentItem((mImageViews.length) * 100);
    }

    private void loadWinnerData(){

//        for(int i=0; i<20; i++){
//            winnerData.add(new WinnerModel());
//        }
//        winningListAdapter.notifyDataSetChanged();


        createHttp(LotteryService.class)
                .getWinnerList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<WinnerModel>>() {
                    @Override
                    public void onSuccess(List<WinnerModel> value) {
                        if(value != null){
                            winnerData.clear();
                            winnerData.addAll(value);
//                            winningListAdapter.notifyDataSetChanged();
                            lv_winning.setAdapter(winningListAdapter);
                        }else{
                            toast(getString(R.string.connection_failed));
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                    }
                });
    }

    @Override
    protected void initData() {

    }

    private int tickViewPager = 0;
    @Subscribe
    public void deliverLotteryList(LotteryListTickEvent event){
        lotteryVOs.clear();
        lotteryVOs.addAll(event.data);
        lotteryGridAdapter.notifyDataSetChanged();

        ++tickViewPager;
        if(tickViewPager >= 3){
            tickViewPager = 0;
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_CODE && resultCode == Activity.RESULT_OK){
            toast("登录成功");
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.txt_login:
                    LoginActivity.startActivity(HomeFragment.this, LOGIN_CODE);
                    break;
                case R.id.txt_register:
                    RegisterActivity.startActivity(getActivity(), REGISTER_CODE);
                    break;
            }
        }
    };

    private android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            lv_winning.smoothScrollToPosition(position);
            lv_winning.smoothScrollBy(3, 150);
            return false;
        }
    });

    private boolean runTick = true;
    private int position = 0;
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
                        ++position;
                        Thread.sleep(10);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     *
     * @author xiaanming
     *
     */
    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
