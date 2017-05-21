package com.forum.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.forum.lottery.R;

/**
 * Created by Su on 2015/9/18.
 */
public class RatioLinearLayout extends LinearLayout {
    private float ratio;
    private boolean isWidthFix = true;
    public RatioLinearLayout(Context context) {
        this(context, null);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioView);
        ratio = a.getFloat(R.styleable.RatioView_ratio, 0);
        isWidthFix = a.getBoolean( R.styleable.RatioView_widthFix,isWidthFix);
        a.recycle();
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setWidthFix(boolean isWidthFix) {
        this.isWidthFix = isWidthFix;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(ratio != 0){
            if(isWidthFix){
                setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * ratio));
            }else{
                setMeasuredDimension((int) (getMeasuredHeight() * ratio), getMeasuredHeight());
            }
        }
    }
}
