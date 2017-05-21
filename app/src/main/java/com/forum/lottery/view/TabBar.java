package com.forum.lottery.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forum.lottery.R;


/**
 * Created by Archer.su on 2014/10/14.
 */
public class TabBar extends LinearLayout {
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    private ColorStateList textColor;
    private int textSize = 14;
    private int tabPaddingTop = 0;
    private int tabPaddingBottom = 0;
    private int tabBackgroundResId;
    private int tabDrawablePadding = 3;
    private int curItem = -1;
    private ViewPager viewPager;
    private LayoutParams defaultLayoutParams;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private PagerAdapter pagerAdapter;
    public TabBar(Context context) {
        super(context);
        init(context);
    }

    public TabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        tabPaddingTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPaddingTop, dm);
        tabPaddingBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPaddingBottom, dm);
        tabDrawablePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabDrawablePadding, dm);
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, dm);

        if (textColor == null)
            textColor = ColorStateList.valueOf(Color.BLACK);

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        textSize = (int) a.getDimensionPixelSize(0, textSize);
        textColor = a.getColorStateList(1);
        a.recycle();

        a = context.obtainStyledAttributes(attrs, R.styleable.TabBar);
        tabBackgroundResId = a.getResourceId(R.styleable.TabBar_stabBackground, 0);
        tabPaddingTop = a.getDimensionPixelSize(R.styleable.TabBar_stabPaddingTop, tabPaddingTop);
        tabPaddingBottom = a.getDimensionPixelSize(R.styleable.TabBar_stabPaddingBottom, tabPaddingBottom);
        tabDrawablePadding = a.getDimensionPixelSize(R.styleable.TabBar_stabDrawablePadding, tabDrawablePadding);
        a.recycle();

        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        defaultLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
    }


    private View createTabItem(CharSequence title, int iconResId) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(defaultLayoutParams);
        textView.setPadding(0, tabPaddingTop, 0, tabPaddingBottom);
        textView.setText(title);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setBackgroundResource(tabBackgroundResId);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, iconResId, 0, 0);
        textView.setCompoundDrawablePadding(tabDrawablePadding);
        textView.setOnClickListener(itemClick);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        if (getChildCount() != 0)
            removeAllViews();
        viewPager.setOnPageChangeListener(itemChangeListener);
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter instanceof IconProvider) {
            addIconItem(pagerAdapter);
        } else {
            addTextItem(pagerAdapter);
        }
        changeTabItem(viewPager.getCurrentItem());
        this.viewPager = viewPager;
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter, int curItem) {
        if(pagerAdapter == null)
            return;
        if(getChildCount() != 0)
            removeAllViews();
        if (pagerAdapter instanceof IconProvider) {
            addIconItem(pagerAdapter);
        } else {
            addTextItem(pagerAdapter);
        }
        this.pagerAdapter = pagerAdapter;
        changeTabItem(curItem);
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter){
        setPagerAdapter(pagerAdapter, 0);
    }

    public void setCurrentItem(int curItem) {
        changeTabItem(curItem);
    }

    private void addIconItem(PagerAdapter pagerAdapter) {
        int count = pagerAdapter.getCount();
        for (int i = 0; i < count; i++) {
            addView(createTabItem(pagerAdapter.getPageTitle(i), ((IconProvider) pagerAdapter).getIconResId(i)));
        }
    }

    private void addTextItem(PagerAdapter pagerAdapter) {
        int count = pagerAdapter.getCount();
        for (int i = 0; i < count; i++) {
            addView(createTabItem(pagerAdapter.getPageTitle(i), 0));
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    private void changeTabItem(int newItem) {
        if (newItem == curItem) {
            return;
        }
        getChildAt(newItem).setSelected(true);
        if (curItem != -1)
            getChildAt(curItem).setSelected(false);
        curItem = newItem;
        if(pagerAdapter != null)
            pagerAdapter.setPrimaryItem(this, curItem, getChildAt(curItem));
    }

    private OnClickListener itemClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = indexOfChild(v);
            changeTabItem(index);
            if(viewPager != null)
                viewPager.setCurrentItem(index,false);
        }
    };

    private ViewPager.OnPageChangeListener itemChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageSelected(position);
            changeTabItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (onPageChangeListener != null)
                onPageChangeListener.onPageScrollStateChanged(state);
        }
    };


    public interface IconProvider {
        int getIconResId(int position);
    }
}
