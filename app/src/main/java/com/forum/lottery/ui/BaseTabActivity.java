package com.forum.lottery.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.forum.lottery.R;
import com.forum.lottery.view.PagerSlidingTabStrip;


/**
 * Created by admin on 2016/8/30.
 */
public abstract class BaseTabActivity extends BaseActionBarActivity {
    private ViewPager pageContent;
    private PagerSlidingTabStrip tabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        tabStrip = findView(R.id.tab_strip);
        pageContent = findView(R.id.page_content);
        pageContent.setAdapter(createPagerAdapter());
        tabStrip.setShouldExpand(true);
        tabStrip.setViewPager(pageContent);
    }


    protected abstract PagerAdapter createPagerAdapter();

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        tabStrip.setOnPageChangeListener(onPageChangeListener);
    }

}
